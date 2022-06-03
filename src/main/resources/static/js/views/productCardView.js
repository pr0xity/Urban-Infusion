import {sendAddToCartRequest} from "../controllers/cartController.js";
import {sendWishlistRequest, setWishlistButtonsOnSingleProduct} from "./wishlistButtonView.js";

/**
 * Implements event listeners on product cards.
 */
export function implementProductCardFunctionality () {
  const products = document.querySelectorAll(".product-card");
  products.forEach(product => {
    setWishlistButtonsOnSingleProduct(product);
    const wishlistButton = product.querySelector(".product__btn--wishlist");
    const addToCartButton = product.querySelector(".product__btn--add-to-cart");
    wishlistButton.addEventListener("click", sendWishlistRequest);
    addToCartButton.addEventListener("click", sendAddToCartRequest);
  });
}