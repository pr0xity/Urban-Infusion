"use strict";

/**
 * Initializes the sign-up form.
 */
const setSignUpForm = function () {
  const firstNameInput = document.querySelector(`input[name="firstName"]`);
  const lastNameInput = document.querySelector(`input[name="lastName"]`);
  const emailInput = document.querySelector(`input[name="email"]`);
  const addressInputs = {
    addressLine1: document.querySelector(`input[name="addressLine1"]`),
    city: document.querySelector(`input[name="city"]`),
    postalCode: document.querySelector(`input[name="postalCode"]`),
    country: document.querySelector(`input[name="country"]`),
  };
  const passwordInput = document.querySelector(
    `input[name="registrationPassword"]`
  );

  /**
   * Returns the sign-up form as an object for request body.
   *
   * @return {{firstName, lastName, country, password: *, city, postalCode, addressLine1, addressLine2, email: string}}
   */
  const getSignUpRequestBody = function () {
    return {
      firstName: document.querySelector(`input[name="firstName"]`).value,
      lastName: document.querySelector(`input[name="lastName"]`).value,
      email: document.querySelector(`input[name="email"]`).value,
      addressLine1: document.querySelector(`input[name="addressLine1"]`).value,
      addressLine2: document.querySelector(`input[name="addressLine2"]`).value,
      postalCode: document.querySelector(`input[name="postalCode"]`).value,
      city: document.querySelector(`input[name="city"]`).value,
      country: document.querySelector(`input[name="country"]`).value,
      password: document.querySelector(`input[name="registrationPassword"]`).value,
    };
  };

  /**
   * Displays error on input field.
   *
   * @param input input field to display error on.
   * @param errormessage the error message to display.
   */
  const setInputError = function (input, errormessage) {
    input.parentElement
      .querySelector(".signup__label")
      .insertAdjacentHTML(
        "beforeend",
        `<span class="input--error-message"> - ${errormessage}</span>`
      );
    input.classList.add("input--error");
  };

  /**
   * Resets the input errors.
   */
  const resetInputError = function () {
    document
      .querySelectorAll(".input--error-message")
      .forEach((message) => message.parentElement.removeChild(message));
    firstNameInput.classList.remove("input--error");
    lastNameInput.classList.remove("input--error");
    emailInput.classList.remove("input--error");
    Object.values(addressInputs).forEach((input) => input.classList.remove("input--error"));
    passwordInput.classList.remove("input--error");
  };

  /**
   * Returns true if all required fields are valid, returns false if not and will show the error
   * on the input fields accordingly if not.
   *
   * @return {boolean} true if all required fields are valid, false if not.
   */
  const isSignUpFormValid = async function () {
    resetInputError();
    const validAddress = await isAddressValid(createAddressStringFromObject(getSignUpRequestBody()));

    if (getSignUpRequestBody().firstName === "") {
      setInputError(firstNameInput, "Field cannot be empty");
    }
    if (getSignUpRequestBody().lastName === "") {
      setInputError(lastNameInput, "Field cannot be empty");
    }
    if (!isEmailAddressValid(getSignUpRequestBody().email)) {
      setInputError(emailInput, "Email is not filled or not valid");
    }
    if (validAddress === false || !isAddressFormValid(addressInputs)) {
      Object.values(addressInputs).forEach((input) => {
        setInputError(input, "Address is not valid")}
      );
    }
    if (getSignUpRequestBody().password === "") {
      setInputError(passwordInput, "Field cannot be empty");
    }

    console.log(!!(getSignUpRequestBody().firstName !== "" &&
      getSignUpRequestBody().lastName !== "" &&
      isEmailAddressValid(getSignUpRequestBody().email) &&
      validAddress &&
      getSignUpRequestBody().password !== ""));

    return !!(getSignUpRequestBody().firstName !== "" &&
      getSignUpRequestBody().lastName !== "" &&
      isEmailAddressValid(getSignUpRequestBody().email) &&
      validAddress &&
      getSignUpRequestBody().password !== "");
  };

  /**
   * Shows the completed registration window.
   */
  const sendRegistrationRequestSuccess = function () {
    resetInputError();
    const completedRegistrationWindow = document.querySelector(".modal");
    const completedCloseButton = completedRegistrationWindow.querySelector(
      ".btn--close"
    );
    showElement(completedRegistrationWindow);

    const closeCompletedWindow = function () {
      hideElement(completedRegistrationWindow);
    };
    completedCloseButton.addEventListener("click", closeCompletedWindow);
    completedRegistrationWindow.addEventListener("click", closeCompletedWindow);
  };

  /**
   * Sends an registration request.
   *
   * @param event
   */
  const sendRegistrationRequest = function (event) {
    event.preventDefault();
    if (isSignUpFormValid() === true) {
      sendApiRequest(
        `${REGISTRATION_API_PATHNAME}`,
        "POST",
        getSignUpRequestBody(),
        sendRegistrationRequestSuccess,
        null,
        null
      );
    }
  };

  document
    .querySelector(`input[name="submitRegistration"]`)
    .addEventListener("click", sendRegistrationRequest);
};

setSignUpForm();