import {reloadCurrentPage, sendApiRequest, WISHLIST_API_PATHNAME} from "../tools.js";
import {setLoginAlert} from "../login.js";

/**
 * Sends a request to add the product with the given product id to the current user's wishlist.
 *
 * @param productId id of the product to add to wishlist.
 * @param successCallback function to invoke upon successful request.
 */
export function sendAddToWishlistRequest(productId, successCallback) {
  sendWishlistRequest(productId, "POST", successCallback).finally();
}

/**
 * Sends a request to remove the product with the given product id from the current user's wishlist.
 *
 * @param productId id of the product to remove from the wishlist.
 * @param successCallback function to invoke upon successful request.
 */
export function sendDeleteFromWishlistRequest(productId, successCallback = null) {
  return sendWishlistRequest(productId, "DELETE", successCallback);
}

/**
 *
 * @param productId
 * @param method
 * @param successCallback
 * @return {Promise<*>}
 */
function sendWishlistRequest(productId, method, successCallback) {
  return sendApiRequest(`${WISHLIST_API_PATHNAME}/${productId}`, method, null, successCallback, wishlistUnauthorizedCallback);
}

/**
 * Displays login menu with an alert.
 */
const wishlistUnauthorizedCallback = function () {
  const userMenuButton = document.querySelector("#user-menu");
  userMenuButton.click();
  setLoginAlert("Log in to save your favourites");
};