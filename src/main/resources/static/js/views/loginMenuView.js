import {hideElement, reloadCurrentPage, showElement} from "../tools.js";
import { sendForgottenPasswordRequest, sendLoginRequest } from "../controllers/authenticationController.js";


const loginMenu = document.querySelector(".login");
const loginButton = document.querySelector("#login-btn");
const loginLoading = document.querySelector(".login .loading");
const loginButtonText = document.querySelector(".login__btn--text");
const loginEmail = document.querySelector("#login-email");
const loginPassword = document.querySelector("#login-password");
const loginAlert = document.querySelector(".login__alert");
const userMenuButton = document.querySelector("#user-menu");

/**
 * Sets the login alert to the given message.
 *
 * @param alertMessage message to be set.
 */
export const setLoginAlert = function (alertMessage) {
  if (loginButton !== null) {
    loginAlert.innerHTML = `${alertMessage}`;
  }
};

/**
 * Sets the login alert to empty.
 */
export const resetLoginAlert = function () {
  if (loginButton !== null) {
    loginAlert.innerHTML = "";
  }
};

/**
 * If login successful, reload page.
 */
export const loginSuccess = function () {
  userMenuButton.click();
  reloadCurrentPage();
};

/**
 * If error logging in set login alert accordingly.
 */
export const loginError = function () {
  setLoginAlert("Wrong username or password");
};

/**
 * Returns the login values used for request body as an object.
 *
 * @return {{password: string, email: string}} the login object.
 */
export const getLoginRequestBody = function () {
  return {
    email: loginEmail.value.toString(),
    password: loginPassword.value.toString(),
  };
};

/**
 * Sends a POST request for log in.
 */
export const loginEvent = function (event) {
  event.preventDefault();
  showElement(loginLoading);
  hideElement(loginButtonText);
  sendLoginRequest(getLoginRequestBody(), loginSuccess, loginError, loginError).finally(() => {
    hideElement(loginLoading);
    showElement(loginButtonText);
  });
};

/**
 * Sets error message for forgotten password event errors.
 */
export const forgottenPasswordError = function () {
  setLoginAlert("Did you type in email?");
};

/**
 * Sets error message for wrong info in forgotten password events.
 */
export const forgottenPasswordUnauthorized = function () {
  setLoginAlert("Could not find user with this email");
};

/**
 * Sets message to check email upon forgotten password event.
 */
export const forgottenPasswordSuccess = function () {
  setLoginAlert("A new password has been sent to you email");
};

/**
 * Sets the event for when forgotten password link is clicked.
 *
 * @param event event of clicking forgotten password link.
 */
export const forgottenPasswordEvent = function (event) {
  event.preventDefault();
  showElement(loginLoading);
  hideElement(loginButtonText);
  sendForgottenPasswordRequest({ email: loginEmail.value.toString() }, forgottenPasswordSuccess, forgottenPasswordUnauthorized, forgottenPasswordError).finally(() => {
    hideElement(loginLoading);
    showElement(loginButtonText);
  });
};