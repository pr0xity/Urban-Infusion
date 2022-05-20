/**********************************************
 * Login handling *
 **********************************************/

const loginButton = document.querySelector("#login-btn");
const loginEmail = document.querySelector("#login-email");
const loginPassword = document.querySelector("#login-password");
const loginAlert = document.querySelector(".login__alert");
const forgottenPasswordButton = document.querySelector("#forgotten-password");

/**
 * Sets the login alert to the given message.
 *
 * @param alertMessage message to be set.
 */
const setLoginAlert = function (alertMessage) {
    if (loginButton !== null) {
        loginAlert.innerHTML = `${alertMessage}`;
    }
};

/**
 * Sets the login alert to empty.
 */
const resetLoginAlert = function () {
    if (loginButton !== null) {
        loginAlert.innerHTML = "";
    }
};

/**
 * If login successful, reload page.
 */
const loginSuccess = function () {
    userMenuButton.click();
    reloadCurrentPage();
}

/**
 * If error logging in set login alert accordingly.
 */
const loginError = function () {
    setLoginAlert("Wrong username or password");
}

/**
 * Returns the login values used for request body as an object.
 *
 * @return {{password: string, email: string}} the login object.
 */
const getLoginRequestBody = function () {
    return {
        email: loginEmail.value.toString(),
        password: loginPassword.value.toString(),
    };
}

/**
 * Sends a POST request for log in.
 */
const sendLoginRequest = function (event) {
    event.preventDefault();
    sendApiRequest(`${AUTHENTICATION_API_PATHNAME}`, "POST", getLoginRequestBody(), loginSuccess, loginError, loginError)
};

const forgottenPasswordError = function () {
    setLoginAlert("Did you type in email?")
}
const forgottenPasswordUnauthorized = function () {
    setLoginAlert("Could not find user with this email")
}
const forgottenPasswordSuccess = function () {
    setLoginAlert("A new password has been sent to you email")
}
const sendForgottenPasswordRequest = function (event) {
    event.preventDefault();
    sendApiRequest(`/forgottenPassword`, "POST", {email: loginEmail.value.toString()}, forgottenPasswordSuccess, forgottenPasswordUnauthorized, forgottenPasswordError);
}

//Adding event listeners if login button is present.
if (loginButton !== null) {
    loginButton.addEventListener("click", sendLoginRequest);
    forgottenPasswordButton.addEventListener("click", sendForgottenPasswordRequest);
}