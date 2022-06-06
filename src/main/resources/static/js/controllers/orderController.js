import { ORDERS_API_PATHNAME, sendApiRequest } from "../tools.js";

/**
 * Sends request to retrieve all orders.
 *
 * @return {Promise<*>} Promise for the get all orders request.
 */
export function sendGetAllOrdersRequest() {
  return sendOrderRequest(null, "GET");
}

/**
 *
 * @return {Promise<*>}
 */
export function sendGetRecentOrdersRequest() {
  return sendOrderRequest("recent");
}

/**
 * Sends request to add new order.
 *
 * @param successCallback function to invoke on success.
 * @param unauthorizedCallback function to invoke on unauthorized error.
 * @param errorCallback function to invoke on general error.
 * @return {Promise<*>} Promise for the new order request.
 */
export function sendPostNewOrderRequest(successCallback, unauthorizedCallback, errorCallback) {
  return sendOrderRequest(null, "POST", null, successCallback, unauthorizedCallback, errorCallback);
}

/**
 * Sends request to update the order with the given id.
 *
 * @param orderId id of the order to update.
 * @param body body of the order to update to.
 * @param successCallback function to invoke upon successful update request.
 * @param unauthorizedCallback function to invoke on unauthorized error.
 * @param errorCallback function to invoke on general error.
 * @return {Promise<*>} Promise for the update order request.
 */
export function sendUpdateOrderRequest(orderId, body, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendOrderRequest(orderId, "PUT", body, successCallback, unauthorizedCallback, errorCallback);
}

/**
 * Sends request to delete the order with the given order id.
 *
 * @param orderId id of the order to update.
 * @param successCallback function to invoke upon successful delete request.
 * @param unauthorizedCallback function to invoke on unauthorized error.
 * @param errorCallback function to invoke on general error.
 * @return {Promise<*>} Promise for delete order request.
 */
export function sendDeleteOrderRequest(orderId, successCallback, unauthorizedCallback, errorCallback) {
  return sendOrderRequest(orderId, "DELETE", successCallback, unauthorizedCallback, errorCallback);
}

/**
 * Sends an API request to the api order endpoint with the given information.
 *
 * @param pathname the pathname to send request to, order id or other request mapping.
 * @param method method to send request as.
 * @param body body of the request, set as null if not needed.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke on unauthorized error, set as null if not needed.
 * @param errorCallback function to invoke on general error, set as null if not needed.
 * @return {Promise<*>} Promise of the request sent.
 */
function sendOrderRequest(pathname = null, method, body = null, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  if (pathname === null) {
    return sendApiRequest(`${ORDERS_API_PATHNAME}`, method, body, successCallback, unauthorizedCallback, errorCallback)
  } else {
    return sendApiRequest(`${ORDERS_API_PATHNAME}/${pathname}`, method, body, successCallback, unauthorizedCallback, errorCallback)
  }
}
