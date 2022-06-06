import {
  IMAGE_API_PATHNAME,
  PRODUCT_API_PATHNAME, PRODUCT_CATEGORY_API_PATHNAME,
  sendApiRequest,
  sendFormDataRequest,
} from "../tools.js";

/**
 * Sends a request to retrieve all products.
 *
 * @return {Promise<*>} Promise for request to retrieve all products.
 */
export function sendGetAllProductsRequest() {
  return sendProductsRequest("all", "GET");
}

/**
 * Sends a request to retrieve all products which has not been deleted (not set as inactive).
 *
 * @return {Promise<*>} Promise for request to retrieve all active products.
 */
export function sendGetAllActiveProductsRequest() {
  return sendProductsRequest("", "GET");
}

/**
 * Sends request to add a products.
 *
 * @param body body of the product to add.
 * @param successCallback function to invoke upon success.
 * @return {Promise<*>} Promise for request to add a product.
 */
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
 * Sends request to get all product categories.
 *
 * @return {Promise<*>} Promise for request to get all product categories.
 */
export function sendGetProductCategoriesRequest() {
  return sendProductCategoriesRequest("", "GET");
}

/**
 * Sends request to get the product category with the specific category id.
 *
 * @param categoryId id of the category to retrieve.
 * @return {Promise<*>} Promise for request to get product category.
 */
export function sendGetOneProductCategoryRequest(categoryId) {
  return sendProductCategoriesRequest(categoryId, "GET");
}

/**
 * Sends a request to retrieve the image for the product with the given product id.
 *
 * @param productId the id of the product to retrieve product for.
 * @param successCallback function to invoke upon success.
 * @return {Promise<Response>} Promise for request to retrieve a products image.
 */
export function sendGetProductImageRequest(productId, successCallback) {
  return sendProductImageRequest(productId, "GET", successCallback);
}

/**
 * Sends request to add image of product.
 *
 * @param data the image of the product to add.
 * @return {Promise<Response>} Promise for request to add image of product.
 */
export function sendAddProductImageRequest(data) {
  return sendProductImageRequest("", "POST", data);
}

/**
 * Sends request to update image of a product.
 *
 * @param productId the id of the product to update image for.
 * @param data the image to update the product's image to.
 * @param successCallback function to invoke upon success.
 * @return {Promise<Response>} Promise for updating product image.
 */
export function sendUpdateProductImageRequest(productId, data, successCallback = null) {
  return sendProductImageRequest(productId, "PUT", data, successCallback);
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
  return sendApiRequest(`${PRODUCT_API_PATHNAME}/${pathVariable}`, method, body, successCallback, unauthorizedCallback, errorCallback)
}

/**
 * Sends an API request to the product categories endpoint with the given information.
 *
 * @param pathVariable path variable for the request to product categories endpoint, set as "" if not needed.
 * @param method the method for the request to product categories endpoint.
 * @param body the body for thr request, set as null if not needed.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set at null if not needed.
 * @param errorCallback function to invoke upon general errors, set as null if not needed.
 * @return {Promise<*>} Promise for the request to product-categories endpoint.
 */
function sendProductCategoriesRequest(pathVariable, method, body = null, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendApiRequest(`${PRODUCT_CATEGORY_API_PATHNAME}/${pathVariable}`, method, body, successCallback, unauthorizedCallback, errorCallback);
}

/**
 * Sends an API request for form data to the products image endpoint with the given information.
 *
 * @param pathVariable path variable for the request to the products image endpoint, set as "" if not needed.
 * @param method the method of the request to send to the products image endpoint.
 * @param data data to send for the request to the products image endpoint.
 * @param successCallback
 * @return {Promise<Response>} Promise for the request sent to the products image endpoint.
 */
function sendProductImageRequest(pathVariable, method, data = null, successCallback = null) {
  return sendFormDataRequest(`${IMAGE_API_PATHNAME}/${pathVariable}`, method, data, successCallback)
}