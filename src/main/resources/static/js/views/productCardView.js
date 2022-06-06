import { sendAddToCartRequest } from "../controllers/cartController.js";
import { sendWishlistRequest, setWishlistButtonsOnProduct } from "./wishlistButtonView.js";
import { getProductIdFromElement, reloadCurrentPage, WISHLIST_PATHNAME } from "../tools.js";
import {sendDeleteFromWishlistRequest, sendGetWishlistRequest} from "../controllers/wishlistController.js";
import { setRatingLeavesOnProductsAndReviews } from "./reviewLeavesView.js";
import {sendGetReviewsOfProduct} from "../controllers/reviewController.js";

/**
 * Implements event listeners on product cards.
 */
export function implementProductCardFunctionality() {
  const products = document.querySelectorAll(".product-card");
  products.forEach((product) => {
    setWishlistButtonsOnProduct(product);
    const wishlistButton = product.querySelector(".product__btn--wishlist");
    const addToCartButton = product.querySelector(".product__btn--add-to-cart");
    wishlistButton.addEventListener("click", sendWishlistRequest);
    addToCartButton.addEventListener("click", addToCartHandler);
  });
  setRatingLeavesOnProductsAndReviews();
}

/**
 * Sends add to cart request and handles it accordingly.
 *
 * @param event event on add to cart button.
 */
function addToCartHandler(event) {
  const productId = getProductIdFromElement(
    event.target.closest(".product__btn--add-to-cart")
  );
  if (window.location.pathname.includes(WISHLIST_PATHNAME)) {
    sendAddToCartRequest(productId).then(() => {
      sendDeleteFromWishlistRequest(productId, reloadCurrentPage).finally();
    });
  } else {
    sendAddToCartRequest(productId).finally();
  }
}

/**
 * Formats the given button in the product card and returns the html
 * of the formatted product card.
 *
 * @param product the product to place in a product card.
 * @return {string} html string of the product in the product card.
 */
export const addProductToProductCard = function (product) {
  return `<article class="product-card container--white" data-productid=${product.id}
    ><img 
      class="product-card__img"
      src="/api/products/images/${product.imageId}"
      alt="${product.name}"
    />
    <div class="product-card__details">
      <a
        href="/products/${product.id}"
        class="product__rating"
        data-rating=${product.averageRating}
        aria-label="This product has an average rating of ${product.averageRating} tea leaves, click to see all reviews'"
      ><img
         class="product__rating--img"
         src="../img/icons/leaf.svg"
         alt=""
         data-rating="1"
      /><img
        class="product__rating--img"
        src="../img/icons/leaf.svg"
        alt=""
        data-rating="2"
      /><img
        class="product__rating--img"
        src="../img/icons/leaf.svg"
        alt=""
        data-rating="3"
      /><img
        class="product__rating--img"
        src="../img/icons/leaf.svg"
        alt=""
        data-rating="4"
      /><img
        class="product__rating--img"
        src="../img/icons/leaf.svg"
        alt=""
        data-rating="5"
      />
      <p></p>
      </a>
      <a href="/products/${product.id}" class="link">
        <h4 class="product-card__heading">${product.name}</h4>
      </a>
      <ul class="product-card__list">
        ${formatDescriptionList(product.description)}
      </ul>
      <div class="product-card__cta">
        <p class="product-card__price">${product.price},-</p>
          <div class="product__btn--wishlist" data-wishlist="false"></div>
            <button
              data-productid=${product.id}
              class="product__btn--add-to-cart"
              aria-label="add item to shopping cart"
            ><i class="ph-tote-light btn--icon"></i>
            </button>
        </div>
    </div>`;
};

/**
 * Formats the description list of the product and returns html of ti.
 *
 * @param description the description list of the product to format.
 * @return {string} the html string of the formatted description.
 */
const formatDescriptionList = function (description) {
  let descriptionInHtml = "";
  description.split(".").forEach((line) => {
    descriptionInHtml += `<li class="product-card__item"><i class="ph-check-thin product-card__icon"></i>${line}</li>`;
  });
  return descriptionInHtml;
};

/**
 * Checks if the product is in the user's wishlist and formats the button
 * accordingly.
 */
export const setWishlistButtonOnProductCards = function () {
  sendGetWishlistRequest().then(wishlist => {
    const productCards = document.querySelectorAll(".product-card");
    productCards.forEach(productCard => {
     let wishlistButton = productCard.querySelector(".product__btn--wishlist");
      wishlist.products.forEach(product => {
        if (product.id === Number(getProductIdFromElement(productCard))) {
          wishlistButton.dataset.wishlist = "true";
        }
      });
      setWishlistButtonsOnProduct(productCard);
      wishlistButton = productCard.querySelector(".product__btn--wishlist");
      wishlistButton.addEventListener("click", sendWishlistRequest);
    })
  })
};

/**
 * Retrieves the number of rating the given product has and places it in the product card.
 *
 * @param product the product to find and set review counter on the product card for.
 */
export const setReviewCount = function (product) {
  sendGetReviewsOfProduct(product.id).then(reviews => {
    const productCards = document.querySelectorAll(".product-card");
    productCards.forEach(productCard => {
      if (product.id === Number(getProductIdFromElement(productCard))) {
        const reviewCounter = productCard.querySelector(
          ".product__rating > p"
        );
        reviewCounter.innerHTML = `(${reviews.length})`;
      }
    })
  })
};
