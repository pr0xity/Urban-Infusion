import {sendApiRequest, reloadCurrentPage, AUTHENTICATION_API_PATHNAME, FORGOTTEN_PASSWORD_API_PATHNAME} from "./tools.js";

/**********************************************
 * Login handling *
 **********************************************/

const loginButton = document.querySelector("#login-btn");
const loginEmail = document.querySelector("#login-email");
const loginPassword = document.querySelector("#login-password");
const loginAlert = document.querySelector(".login__alert");
const forgottenPasswordButton = document.querySelector("#forgotten-password");
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
}

/**
 * If error logging in set login alert accordingly.
 */
export const loginError = function () {
    setLoginAlert("Wrong username or password");
}

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
}

/**
 * Sends a POST request for log in.
 */
export const sendLoginRequest = function (event) {
    event.preventDefault();
    sendApiRequest(`${AUTHENTICATION_API_PATHNAME}`, "POST", getLoginRequestBody(), loginSuccess, loginError, loginError).finally();
};

export const forgottenPasswordError = function () {
    setLoginAlert("Did you type in email?")
}
export const forgottenPasswordUnauthorized = function () {
    setLoginAlert("Could not find user with this email")
}
export const forgottenPasswordSuccess = function () {
    setLoginAlert("A new password has been sent to you email")
}
export const sendForgottenPasswordRequest = function (event) {
    event.preventDefault();
    sendApiRequest(`${FORGOTTEN_PASSWORD_API_PATHNAME}`, "POST", {email: loginEmail.value.toString()}, forgottenPasswordSuccess, forgottenPasswordUnauthorized, forgottenPasswordError).finally();
}
