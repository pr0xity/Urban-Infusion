import { RATING_API_PATHNAME, sendApiRequest } from "../tools.js";

export function sendGetAllReviewsRequest() {
  return sendReviewRequest("", "GET");
}

export function sendGetRecentReviewsRequest() {
  return sendReviewRequest("recent", "GET");
}

export function sendGetReviewsOfProduct(productId) {
  return sendReviewRequest(`products/${productId}`, "GET");
}

export function sendAddNewReviewRequest(productId, body, successCallback, unauthorizedCallback) {
  return sendReviewRequest(productId, "POST", body, successCallback, unauthorizedCallback);
}

export function sendUpdateReviewRequest(productId, body, successCallback, unauthorizedCallback, errorCallback) {
  return sendReviewRequest(productId, "PUT", body, successCallback, unauthorizedCallback, errorCallback);
}

export function sendDeleteReviewRequest(productId, body = null, successCallback, unauthorizedCallback) {
  return sendReviewRequest(productId, "DELETE", body, successCallback, unauthorizedCallback);
}

function sendReviewRequest(pathVariable, method, body = null, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendApiRequest(`${RATING_API_PATHNAME}/${pathVariable}`, method, body, successCallback, unauthorizedCallback, errorCallback);
}