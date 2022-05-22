const LEAF_SELECTED = "../img/icons/leaf-fill.svg";
const LEAF_UNSELECTED = "../img/icons/leaf.svg";

/**
 * Returns the rounded rating from dataset of the given element.
 *
 * @param element the element to retrieve rating from.
 * @returns {number} the rounded rating.
 */
function getRatingFromElement(element) {
  return Math.round(Number(element.dataset.rating));
}

/**
 * Makes the leaves which has a data rating attribute less than
 * or equal the given rating to selected.
 *
 * @param leaves the leaves to check and set as selected.
 * @param {*} rating the rating to make leaves selected for.
 */
function setLeavesToSelected(leaves, rating) {
  leaves.forEach((leaf) => {
    if (getRatingFromElement(leaf) <= rating) {
      leaf.src = LEAF_SELECTED;
    } else {
      leaf.src = LEAF_UNSELECTED;
    }
  });
}

/**
 * Sets the rating leaves to filled on the various product(s) and reviews.
 */
function setRatingLeavesOnProductsAndReviews() {
  const ratings = document.querySelectorAll(".product__rating");
  ratings.forEach((rating) => {
    const leaves = rating.querySelectorAll(".product__rating--img");
    setLeavesToSelected(leaves, getRatingFromElement(rating));
  });
}

/**
 * Sets functionality and request handling on the review form on a products page.
 */
function setReviewHandling() {
  const productId = getProductIdFromElement(document.querySelector(".product"));
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
      sendApiRequest(
        `${RATING_API_PATHNAME}/${productId}`,
        "POST",
        getBodyForReviewRequest(),
        reviewRequestSuccess,
        reviewRequestUnauthorized
      );
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
      sendApiRequest(
        `${RATING_API_PATHNAME}/${productId}`,
        "PUT",
        getBodyForReviewRequest(),
        reviewRequestSuccess,
        reviewRequestUnauthorized,
        reviewRequestError
      );
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
      sendApiRequest(
        `${RATING_API_PATHNAME}/${productId}`,
        "DELETE",
        getBodyForReviewRequest(),
        reviewRequestSuccess,
        reviewRequestUnauthorized
      );
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

/**
 * Sets the products page, implements changes when screen size changes.
 */
const setProductPage =  function () {
  const footer = document.querySelector(".footer");
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


  if (mobileLayoutSize.matches) {
    footerObserver.observe(footer);
  } else {
    productCta.classList.remove("product__price-cta--sticky");
    productCta.classList.remove("product__price-cta--absolute");
    document.querySelector(".section-product-info").appendChild(productCta);
    footerObserver.unobserve(footer);
  }

}

// Only on a products page.
if (window.location.pathname.includes(PRODUCT_PATHNAME)) {
  mobileLayoutSize.addEventListener("change", setProductPage);
  setProductPage();
  setReviewHandling();
}
setRatingLeavesOnProductsAndReviews();