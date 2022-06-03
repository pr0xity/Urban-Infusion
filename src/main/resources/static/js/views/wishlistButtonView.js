/**********************************************
 * Wishlist view *
 **********************************************/

import { getProductIdFromElement } from "../tools.js";
import { sendAddToWishlistRequest, sendDeleteFromWishlistRequest } from "../controllers/wishlistController.js";

const filledHeartIcon = `<i class="ph-heart-fill btn--icon btn--selected"></i>`;
const outlinedHeartIcon = `<i class="ph-heart-light btn--icon"></i>`;
const brokenHeartIcon = `<i class="ph-heart-break-light btn--icon"></i>`;
const wishlistButtonClass = ".product__btn--wishlist";

let currentButton;

/**
 * Creates a button element for wishlist and adds the given attributes to it.
 *
 * @param dataWishlist "true" if it is in the wishlist, "false" if not.
 * @param dataProductId the product id.
 * @param ariaLabel aria-label of the buttons action.
 * @returns {HTMLButtonElement} the resulting button element.
 */
const createWishlistButton = function (dataWishlist, dataProductId, ariaLabel) {
  const button = document.createElement("button");
  button.classList.add("product__btn--wishlist");
  button.dataset.wishlist = dataWishlist;
  button.dataset.productid = dataProductId;
  button.ariaLabel = ariaLabel;
  return button;
};

/**
 * Returns whether the product belonging to the button is in the wishlist.
 *
 * @param button button containing data-wishlist.
 * @returns {boolean} "true" if it is in the wishlist, "false" if not.
 */
const isProductInWishlist = function (button) {
  return button.dataset.wishlist === "true";
};

/**
 * Replaces current icon with a filled heart icon.
 *
 * @param button button to replace icon on.
 */
const addFilledHeartIcon = function (button) {
  button.insertAdjacentHTML("afterbegin", filledHeartIcon);
};

/**
 * Replaces current icon with an outlined heart icon.
 *
 * @param button button to replace icon on.
 */
const addOutlinedHeartIcon = function (button) {
  button.insertAdjacentHTML("afterbegin", outlinedHeartIcon);
};

/**
 * Replaces current icon with a broken heart icon.
 *
 * @param button button to replace icon on.
 */
const addBrokenHeartIcon = function (button) {
  button.insertAdjacentHTML("afterbegin", brokenHeartIcon);
};

/**
 * Changes the heart icon on the given button to its opposite
 * (outlined heart / broken heart)
 * (filled heart / outlined heart)
 *
 * @param button the button to change icon of.
 */
const addHeartIcon = function (button) {
  if (isProductInWishlist(button) && window.location.pathname === "/wishlist") {
    addBrokenHeartIcon(button);
  } else if (isProductInWishlist(button)) {
    addFilledHeartIcon(button);
  } else if (!isProductInWishlist(button)) {
    addOutlinedHeartIcon(button);
  }
};

/**
 * Creates a remove from wishlist button and returns it.
 *
 * @param dataProductId the id of the product this button belongs to.
 * @returns {HTMLButtonElement} the remove from wishlist button element.
 */
const createRemoveFromWishlistButton = function (dataProductId) {
  const removeButton = createWishlistButton(
    "true",
    dataProductId,
    "Remove this item from your favourites"
  );
  addHeartIcon(removeButton);
  return removeButton;
};

/**
 * Creates an add to wishlist button and returns it.
 *
 * @param dataProductId the id of the product this button belongs to.
 * @returns {HTMLButtonElement} the add to wishlist button element.
 */
const createAddToWishlistButton = function (dataProductId) {
  const addButton = createWishlistButton(
    "false",
    dataProductId,
    "Add this item to your favourites"
  );
  addHeartIcon(addButton);
  return addButton;
};

/**
 * Replaces the given wishlist button with another.
 *
 * @param wishlistButton the wishlist button to replace.
 * @param dataProductId the id of the product the button belongs to.
 */
const replaceWishlistButton = function (wishlistButton, dataProductId) {
  if (isProductInWishlist(wishlistButton)) {
    wishlistButton.parentNode.replaceChild(
      createRemoveFromWishlistButton(dataProductId),
      wishlistButton
    );
  } else if (!isProductInWishlist(wishlistButton)) {
    wishlistButton.parentNode.replaceChild(
      createAddToWishlistButton(dataProductId),
      wishlistButton
    );
  }
  addHeartIcon(wishlistButton);
};

/**
 * Sets wishlist buttons on pages with multiple productions.
 */
/*export const setWishlistButtonsOnMultipleProducts = function () {
  const products = document.querySelectorAll(".product-card");
  products.forEach((product) => {
    const wishlistButton = product.querySelector(".product__btn--wishlist");
    const dataProductId = getProductIdFromElement(product);
    replaceWishlistButton(wishlistButton, dataProductId);
  });
};*/

/**
 * Sets wishlist button on a product page.
 */
export const setWishlistButtonsOnSingleProduct = function (product) {
  const wishlistButton = product.querySelector(".product__btn--wishlist");
  const dataProductId = getProductIdFromElement(product);
  replaceWishlistButton(wishlistButton, dataProductId);
};

/**
 * Sets data-wishlist attribute to true and returns the setWishlistButtons reference.
 */
const wishlistAddSuccessCallback = function () {
  currentButton.dataset.wishlist = "true";
  currentButton.innerHTML = "";
  addHeartIcon(currentButton);
};

/**
 * Sets data-wishlist attribute to false and returns the setWishlistButtons reference.
 */
const wishlistRemoveSuccessCallback = function () {
  currentButton.dataset.wishlist = "false";
  currentButton.innerHTML = "";
  addHeartIcon(currentButton);
};

/**
 * Sends either a DELETE request or a POST request to add or remove a
 * product to or from the wishlist.
 *
 * @param event
 */
export const sendWishlistRequest = function (event) {
  currentButton = event.target.closest(wishlistButtonClass);
  const productId = getProductIdFromElement(currentButton);

  if (isProductInWishlist(currentButton)) {
    sendDeleteFromWishlistRequest(productId, wishlistRemoveSuccessCallback)
  } else if (!isProductInWishlist(currentButton)) {
    sendAddToWishlistRequest(productId, wishlistAddSuccessCallback);
  }
};

const wishlistButtons = document.querySelectorAll(wishlistButtonClass);

//Adding event listeners
if (wishlistButtons.length !== 0) {
  wishlistButtons.forEach((wishlistButton) => {
    wishlistButton.addEventListener("click", sendWishlistRequest);
  });
}
