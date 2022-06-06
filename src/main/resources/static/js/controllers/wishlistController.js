import { sendApiRequest, WISHLIST_API_PATHNAME } from "../tools.js";

/**
 * Sends a request to get the wishlist for the current user.
 *
 * @return {Promise<*>} Promise for the request to get the current user's wishlist.
 */
export function sendGetWishlistRequest() {
  return sendWishlistRequest("", "GET");
}

/**
 * Sends a request to add the product with the given product id to the current user's wishlist.
 *
 * @param productId id of the product to add to wishlist.
 * @param successCallback function to invoke upon successful request.
 * @param unauthorizedCallback function to invoke upon unauthorized error.
 * @return {*} Promise for add to wishlist request.
 */
export function sendAddToWishlistRequest(productId, successCallback, unauthorizedCallback = null) {
  return sendWishlistRequest(productId, "POST", successCallback, unauthorizedCallback);
}

/**
 * Sends a request to remove the product with the given product id from the current user's wishlist.
 *
 * @param productId id of the product to remove from the wishlist.
 * @param successCallback function to invoke upon successful request.
 * @param unauthorizedCallback function to invoke upon unauthorized error.
 * @return {*} Promise for delete from wishlist request.
 */
export function sendDeleteFromWishlistRequest(productId, successCallback = null, unauthorizedCallback = null) {
  return sendWishlistRequest(productId, "DELETE", successCallback, unauthorizedCallback);
}


/**
 * Sends an API request to the wishlist endpoint with the given information.
 *
 * @param productId the id of the product for the request, set as "" if not needed.
 * @param method method of the request.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @return {*} Promise for request sent to wishlist endpoint.
 */
function sendWishlistRequest(productId, method, successCallback = null, unauthorizedCallback = null) {
  return sendApiRequest(`${WISHLIST_API_PATHNAME}/${productId}`, method, null, successCallback, unauthorizedCallback);
}
