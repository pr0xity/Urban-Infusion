import {IMAGE_API_PATHNAME, PRODUCT_API_PATHNAME, sendApiRequest, sendFormDataRequest} from "../tools.js";

/**
 * Sends a request to retrieve all products.
 *
 * @return {Promise<*>} Promise for request to retrieve all products.
 */
export function sendGetAllProductsRequest() {
  return sendProductsRequest("", "GET");
}

export function sendAddProductRequest(body, successCallback) {
  return sendProductsRequest("", "POST", body, successCallback);
}

/**
 * Sends a request to update the product with the given product id.
 *
 * @param productId the id of the product to send update request for.
 * @param body body of the product to update to.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @return {Promise<*>} Promise for the request to update the given product.
 */
export function sendUpdateProductRequest(productId, body, successCallback) {
  return sendProductsRequest(productId, "PUT", body, successCallback);
}

/**
 * Sends a request to retrieve the image for the product with the given product id.
 *
 * @param productId the id of the product to retrieve product for.
 * @return {Promise<Response>} Promise for request to retrieve a products image.
 */
export function sendGetProductImageRequest(productId) {
  return sendProductImageRequest(productId, "GET");
}

/**
 * Sends request to update the image of the product with the given product id.
 *
 * @param productId if of the product to update image for.
 * @param data the data of the image to update to.
 * @return {Promise<Response>} Promise for the request to update image.
 */
export function sendAddProductImageRequest(productId, data) {
  return sendProductImageRequest(productId, "POST", data);
}

/**
 * Sends request to update the image of the product with the given product id.
 *
 * @param productId if of the product to update image for.
 * @param data the data of the image to update to.
 * @return {Promise<Response>} Promise for the request to update image.
 */
export function sendUpdateProductImageRequest(productId, data) {
  return sendProductImageRequest(productId, "PUT", data);
}

/**
 * Sends an API request to the products' endpoint with the given information.
 *
 * @param pathVariable path variable for the request to the products' endpoint, set as "" if not needed.
 * @param method the method of the request to send to the products' endpoint.
 * @param body body of the request to send to products' endpoint, set as null if not needed.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @param errorCallback function to invoke upon general error, set as null if not needed.
 * @return {Promise<*>} Promise for the request sent to the product's endpoint.
 */
function sendProductsRequest(pathVariable, method, body = null, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendApiRequest(`${PRODUCT_API_PATHNAME}${pathVariable}`, method, body, successCallback, unauthorizedCallback, errorCallback)
}

/**
 * Sends an API request for form data to the products image endpoint with the given information.
 *
 * @param pathVariable path variable for the request to the products image endpoint, set as "" if not needed.
 * @param method the method of the request to send to the products image endpoint.
 * @param data data to send for the request to the products image endpoint.
 * @return {Promise<Response>} Promise for the request sent to the products image endpoint.
 */
function sendProductImageRequest(pathVariable, method, data = null) {
  return sendFormDataRequest(`${IMAGE_API_PATHNAME}/${pathVariable}`, method, data)
}