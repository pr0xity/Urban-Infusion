import {mobileLayoutSize, getProductIdFromElement, reloadCurrentPage} from "./tools.js";
import {sendAddNewReviewRequest, sendDeleteReviewRequest, sendUpdateReviewRequest,} from "./controllers/reviewController.js";
import {getRatingFromElement, LEAF_UNSELECTED, setLeavesToSelected, setRatingLeavesOnProductsAndReviews,} from "./views/reviewLeavesView.js";
import {sendWishlistRequest, setWishlistButtonsOnProduct} from "./views/wishlistButtonView.js";
import {sendAddToCartRequest, setIncrementCounter} from "./controllers/cartController.js";

const product = document.querySelector(".product");
const productId = getProductIdFromElement(document.querySelector(".product"));

/**
 * Sets functionality and request handling on the review form on a products page.
 */
function setReviewHandling() {
  const ratingReview = document.querySelector("#review__rating");
  const reviewRatingLeaves = ratingReview.querySelectorAll(
    ".product__rating--img"
  );
  let isRatingChosen = false;
  let rating;

  /**
   * Sets all the leaves unselected.
   */
  function setAllLeavesToUnselected() {
    reviewRatingLeaves.forEach((leaf) => (leaf.src = LEAF_UNSELECTED));
  }

  /**
   * Makes all the leaves up to the hovered leaf selected.
   */
  function ratingsMouseOverHandler(event) {
    const hoveredRating = getRatingFromElement(event.target);
    setLeavesToSelected(reviewRatingLeaves, hoveredRating);
  }

  /**
   * If no rating is selected, set all leaves to unselected.
   */
  function ratingsMouseLeaveHandler() {
    if (isRatingChosen) {
      setLeavesToSelected(reviewRatingLeaves, rating);
    } else {
      setAllLeavesToUnselected();
    }
  }

  /**
   * Selects the rating from the clicked element.
   */
  function ratingsMouseClickHandler(event) {
    rating = getRatingFromElement(event.target);
    isRatingChosen = true;
  }

  // Adding events to the leaves on the review form.
  reviewRatingLeaves.forEach((leaf) => {
    leaf.addEventListener("mouseover", ratingsMouseOverHandler);
    leaf.addEventListener("mouseleave", ratingsMouseLeaveHandler);
    leaf.addEventListener("click", ratingsMouseClickHandler);
  });

  /**
   * Returns true if necessary fields are filled (email and rating), false if not.
   *
   * @returns {boolean} true if necessary fields are filled, false if not.
   */
  const isReviewFormValid = function () {
    const email = document.querySelector('input[name="email"]');
    const reviewRating = document.querySelector(`input[name="rating"]:checked`);
    return email.value !== "" && reviewRating !== null;
  };

  /**
   * Creates a request body object from the review form fields and returns the object.
   *
   * @returns {{displayName: string, rating, comment, email: string}} the request body object.
   */
  function getBodyForReviewRequest() {
    const displayName = document.querySelector('input[name="displayName"]');
    const email = document.querySelector('input[name="email"]');
    const reviewRating = document.querySelector(`input[name="rating"]:checked`);
    const comment = document.querySelector('textarea[name="comment"]');

    return {
      displayName: displayName.value.toString(),
      email: email.value.toString(),
      rating: reviewRating.value,
      comment: comment.value,
    };
  }

  const sendReview = document.querySelector('input[name="sendReview"]');
  const updateReview = document.querySelector('input[name="updateReview"]');
  const deleteReview = document.querySelector('input[name="deleteReview"]');
  const reviewAlert = document.querySelector(".review__alert");

  /**
   * Sets the review alert ot the given alert message.
   *
   * @param alertMessage alert message to set on review alert.
   */
  const setReviewAlert = function (alertMessage) {
    reviewAlert.innerHTML = `${alertMessage}`;
  };

  /**
   * Sets the review alert to empty.
   */
  const resetReviewAlert = function () {
    reviewAlert.innerHTML = "";
  };

  /**
   * Reloads the page.
   */
  const reviewRequestSuccess = function () {
    resetReviewAlert();
    reloadCurrentPage();
  };

  /**
   * Sets review alert.
   */
  const reviewRequestUnauthorized = function () {
    resetReviewAlert();
    setReviewAlert("Please log in to send a review");
  };

  /**
   *
   */
  const reviewRequestError = function () {
    setReviewAlert("Please type in your own email");
  };

  /**
   * Sends a POST request for a new rating.
   */
  const sendReviewRequest = function (event) {
    event.preventDefault();
    if (isReviewFormValid()) {
      sendAddNewReviewRequest(
        productId,
        getBodyForReviewRequest(),
        reviewRequestSuccess,
        reviewRequestUnauthorized
      ).finally();
    } else {
      setReviewAlert("Email and a selected rating is required for a review");
    }
  };

  /**
   * Sends a PUT request to update a rating.
   */
  const updateReviewRequest = function (event) {
    event.preventDefault();
    if (isReviewFormValid()) {
      sendUpdateReviewRequest(
        productId,
        getBodyForReviewRequest(),
        reviewRequestSuccess,
        reviewRequestUnauthorized,
        reviewRequestError
      ).finally();
    } else {
      setReviewAlert("Email and a selected rating is required for a review");
    }
  };

  /**
   * Sends a DELETE request to delete a rating.
   */
  const deleteReviewRequest = function (event) {
    event.preventDefault();
    if (isReviewFormValid()) {
      sendDeleteReviewRequest(
        productId,
        getBodyForReviewRequest(),
        reviewRequestSuccess,
        reviewRequestUnauthorized
      ).finally();
    } else {
      setReviewAlert("Please fill in your email to delete your review");
    }
  };

  // Adding event listener to the button which is not null.
  if (sendReview !== null) {
    sendReview.addEventListener("click", sendReviewRequest);
  }
  if (updateReview !== null) {
    rating = getRatingFromElement(document.querySelector(".review__form"));
    const ratingRadioButton = document.querySelector(
      `input[value="${rating}"]`
    );
    ratingRadioButton.checked = true;
    isRatingChosen = true;
    setLeavesToSelected(reviewRatingLeaves, rating);

    updateReview.addEventListener("click", updateReviewRequest);
  }
  if (deleteReview !== null) {
    deleteReview.addEventListener("click", deleteReviewRequest);
  }
}

const productCta = document.querySelector(".product__price-cta");
const productCtaHeight = productCta.getBoundingClientRect().height;

const stickyCta = function (entries) {
  const [entry] = entries;

  if (entry.isIntersecting) {
    productCta.classList.add("product__price-cta--absolute");
    productCta.classList.remove("product__price-cta--sticky");
    document.querySelector("main").appendChild(productCta);
  } else {
    productCta.classList.add("product__price-cta--sticky");
    productCta.classList.remove("product__price-cta--absolute");
    document.querySelector(".section-product-info").appendChild(productCta);
  }
};

const footerObserver = new IntersectionObserver(stickyCta, {
  root: null,
  threshold: 0,
  rootMargin: `${productCtaHeight}px`,
});

/**
 * Sets the products page, implements changes when screen size changes.
 */
const responsiveProductPage = function () {
  const footer = document.querySelector(".footer");

  if (mobileLayoutSize.matches) {
    footerObserver.observe(footer);
  } else {
    productCta.classList.remove("product__price-cta--sticky");
    productCta.classList.remove("product__price-cta--absolute");
    document.querySelector(".section-product-info").appendChild(productCta);
    footerObserver.unobserve(footer);
  }
};

setRatingLeavesOnProductsAndReviews();
setWishlistButtonsOnProduct(product);
setIncrementCounter().finally();
(function implementProductFunctionality() {
  function addToCartEvent(event) {
    event.preventDefault();
    sendAddToCartRequest(productId);
  }
  
  const addToCartButton = document.querySelector(".product__btn--add-to-cart");
  const wishlistButton = document.querySelector(".product__btn--wishlist");
  addToCartButton.addEventListener("click", addToCartEvent);
  wishlistButton.addEventListener("click", sendWishlistRequest)

})()
mobileLayoutSize.addEventListener("change", responsiveProductPage);
responsiveProductPage();
setReviewHandling();
