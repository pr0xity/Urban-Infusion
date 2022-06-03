import {RATING_API_PATHNAME, sendApiRequest} from "../tools.js";

export function sendGetAllReviewsRequest() {
  return sendReviewRequest("", "GET");
}

export function sendGetRecentReviewsRequest() {
  return sendReviewRequest("recent", "GET");
}

export function sendAddNewReviewRequest(){
  return null;
}

export function sendUpdateReviewRequest(productId, body, successCallback) {
  return sendReviewRequest(productId, "PUT", body, successCallback);
}

export function sendDeleteReviewRequest(productId, body = null, successCallback) {
  return sendReviewRequest(productId, "DELETE", body, successCallback);
}

function sendReviewRequest(pathVariable, method, body = null, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendApiRequest(`${RATING_API_PATHNAME}/${pathVariable}`, method, body, successCallback, unauthorizedCallback, errorCallback);
}