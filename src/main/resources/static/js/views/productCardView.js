import {sendAddToCartRequest} from "../controllers/cartController.js";
import {sendWishlistRequest, setWishlistButtonsOnProduct} from "./wishlistButtonView.js";
import {getProductIdFromElement, reloadCurrentPage, WISHLIST_PATHNAME} from "../tools.js";
import {sendDeleteFromWishlistRequest} from "../controllers/wishlistController.js";
import {setRatingLeavesOnProductsAndReviews} from "./reviewLeavesView.js";

/**
 * Implements event listeners on product cards.
 */
export function implementProductCardFunctionality () {
  const products = document.querySelectorAll(".product-card");
  products.forEach(product => {
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
function addToCartHandler (event) {
  const productId = getProductIdFromElement(event.target.closest(".product__btn--add-to-cart"));
  if (window.location.pathname.includes(WISHLIST_PATHNAME)) {
    sendAddToCartRequest(productId).then(() => {
      sendDeleteFromWishlistRequest(productId, reloadCurrentPage).finally();
    });
  } else {
    sendAddToCartRequest(productId).finally();
  }
}