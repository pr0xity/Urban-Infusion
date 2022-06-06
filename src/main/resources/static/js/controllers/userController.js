import { sendApiRequest, USERS_API_PATHNAME } from "../tools.js";

/**
 * Sends a request to retrieve all users.
 *
 * @return {*} Promise for request to retrieve all users.
 */
export function sendGetAllUsersRequest() {
  return sendUserRequest("", "GET");
}

/**
 * Sends a request to retrieve the newest users.
 *
 * @return {*} Promise for request to retrieve the newest users.
 */
export function sendGetNewUsersRequest() {
  return sendUserRequest("new", "GET");
}

/**
 * Sends request to update the user with the given user id.
 *
 * @param userId id of the user to update.
 * @param body the updated information of the user.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @param errorCallback function to invoke upon general error, set as null if not needed.
 * @return {*} Promise for request to update user.
 */
export function sendUpdateUserRequest(userId, body, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendUserRequest(userId, "PUT", body, successCallback, unauthorizedCallback, errorCallback);
}

/**
 * Sends request to delete (set as disabled) the user with the given email.
 *
 * @param email the email of the user to delete (set as disabled)
 * @param body body for the request containing password for validation.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @param errorCallback function to invoke upon general error, set as null if not needed.
 * @return {*} Promise for request to delete user.
 */
export function sendDeleteUserRequest(email, body, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendUserRequest(email, "DELETE", body, successCallback, unauthorizedCallback, errorCallback)
}

/**
 * Sends a request to the users' endpoint with the given information.
 *
 * @param pathVariable path variable for the request to the users' endpoint, set as "" if not needed.
 * @param method the method for the request to send.
 * @param body body of the request to send, set as null if not needed.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @param errorCallback function to invoke upon general error, set as null if not needed.
 * @return {*} Promise for the request sent to the users' endpoint.
 */
function sendUserRequest(pathVariable, method, body = null, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendApiRequest(`${USERS_API_PATHNAME}/${pathVariable}`, method, body, successCallback, unauthorizedCallback, errorCallback);
}