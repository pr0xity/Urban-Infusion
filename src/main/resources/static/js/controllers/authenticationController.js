import { AUTHENTICATION_API_PATHNAME, FORGOTTEN_PASSWORD_API_PATHNAME, REGISTRATION_API_PATHNAME, sendApiRequest } from "../tools.js";

/**
 * Sends login request.
 *
 * @param body body containing login information.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @param errorCallback function to invoke upon general error, set as null if not needed.
 * @return {*} Promise for the login request.
 */
export const sendLoginRequest = function (body, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendApiRequest(`${AUTHENTICATION_API_PATHNAME}`, "POST", body, successCallback, unauthorizedCallback, errorCallback);
}

/**
 * Sends registration request
 *
 * @param body body containing registration information.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @param errorCallback function to invoke upon general error, set as null if not needed.
 * @return {*} Promise for the registration request.
 */
export const sendRegistrationRequest = function (body, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendApiRequest(`${REGISTRATION_API_PATHNAME}`, "POST", body, successCallback, unauthorizedCallback, errorCallback);
}

/**
 * Sends forgotten password request.
 *
 * @param body body containing email for the user who forgot their password.
 * @param successCallback function to invoke upon success, set as null if not needed.
 * @param unauthorizedCallback function to invoke upon unauthorized error, set as null if not needed.
 * @param errorCallback function to invoke upon general error, set as null if not needed.
 * @return {*} Promise for the forgotten password request.
 */
export const sendForgottenPasswordRequest = function (body, successCallback = null, unauthorizedCallback = null, errorCallback = null) {
  return sendApiRequest(`${FORGOTTEN_PASSWORD_API_PATHNAME}`, "POST", body, successCallback, unauthorizedCallback, errorCallback);
}