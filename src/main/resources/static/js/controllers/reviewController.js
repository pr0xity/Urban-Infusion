import { RATING_API_PATHNAME, sendApiRequest } from "../tools.js";

/**
 * Sends request to get all reviews.
 *
 * @return {Promise<*>} Promise for request to get all reviews.
 */
export function sendGetAllReviewsRequest() {
  return sendReviewRequest("", "GET");
}

/**
 * Sends request to get recent reviews.
 *
 * @return {Promise<*>} Promise for request to get recent reviews.
 */
export function sendGetRecentReviewsRequest() {
  return sendReviewRequest("recent", "GET");
}

/**
 * Sends request to get reviews of the product with the product id.
 *
 * @param productId id of the product to get reviews of.
 * @return {Promise<*>} Promise for request to get reviews of product.
 */
export function sendGetReviewsOfProduct(productId) {
  return sendReviewRequest(`products/${productId}`, "GET");
}

/**
 * Sends add new review request to the product with the given id.
 *
 * @param productId id of the product to add review to.
 * @param body body with the review data.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @return {Promise<*>} Promise for add new review request.
 */
export function sendAddNewReviewRequest(productId, body, successCallback = null, unauthorizedCallback = null) {
  return sendReviewRequest(productId, "POST", body, successCallback, unauthorizedCallback);
}

/**
 * Sends update review request for review of the product with the given id.
 *
 * @param productId id of the product to update review on.
 * @param body body of the updated review data.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @param errorCallback function to invoke upon general error, set as null if not needed.
 * @return {Promise<*>} Promise for update review request.
 */
export function sendUpdateReviewRequest(productId, body, successCallback, unauthorizedCallback, errorCallback) {
  return sendReviewRequest(productId, "PUT", body, successCallback, unauthorizedCallback, errorCallback);
}

/**
 * Sends delete review request for review on the product with the given id.
 *
 * @param productId id of the product to delete review from.
 * @param body body of the review to delete.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @return {Promise<*>} Promise for delete review request.
 */
export function sendDeleteReviewRequest(productId, body = null, successCallback, unauthorizedCallback) {
  return sendReviewRequest(productId, "DELETE", body, successCallback, unauthorizedCallback);
}

/**
 * Sends an API request to the review/rating endpoint.
 *
 * @param pathVariable path variable of the request, set as "" if not needed.
 * @param method method of the request.
 * @param body body of the request.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @param errorCallback function to invoke upon general error, set as null if not needed.
 * @return {Promise<*>} Promise for the API request sent.
 */
function sendReviewRequest(pathVariable, method, body = null, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendApiRequest(`${RATING_API_PATHNAME}/${pathVariable}`, method, body, successCallback, unauthorizedCallback, errorCallback);
}
