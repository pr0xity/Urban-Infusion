import { implementProductCardFunctionality } from "./views/productCardView.js";
import { setIncrementCounter } from "./controllers/cartController.js";
import {hideElement, showElement, URL, WISHLIST_PATHNAME} from "./tools.js";

const shareWishlistButton = document.querySelector(".wishlist__btn--share");
shareWishlistButton.addEventListener("click", event => {
  event.preventDefault();
  const sharedWishlistLink = `${URL}${WISHLIST_PATHNAME}/shared/${shareWishlistButton.dataset.token}`;
  const sharedWishlistLinkElement = document.querySelector(".wishlist__link--share");
  const sharedWishlistModal = document.querySelector(".wishlist__modal");
  const closeButton = document.querySelector(".btn--close");

  closeButton.addEventListener("click", () => hideElement(sharedWishlistModal));
  sharedWishlistLinkElement.innerHTML = `${sharedWishlistLink}`;
  navigator.clipboard.writeText(sharedWishlistLink).finally(() => showElement(sharedWishlistModal));
})
implementProductCardFunctionality();
setIncrementCounter().finally();
