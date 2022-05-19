const tag_types = ["name", "email", "password", "address"];
const tags_array = ["modal", "open", "close", "field", "edit"];
const tags_map = createTagsMap();

const delete_tags = [
  document.getElementById("modal_delete"),
  document.querySelector(".account-info__btn--delete"),
  document.getElementById("close_delete"),
];

addListeners();

/**
 * Create a map of tags to hold tag references.
 *
 * @returns {Map<string, any>} where key is tag type and value is tag reference
 */
function createTagsMap() {
  const map = new Map();
  for (const type of tag_types) {
    const tags = new Array(tags_array.length);
    for (const [index, element] of tags_array.entries()) {
      const tag_string = element + "_" + type;
      tags[index] = document.getElementById(tag_string);
    }
    map.set(type, tags);
  }
  return map;
}

/**
 * Carries out the creation of listeners.
 */
function addListeners() {
  for (const type of tag_types) {
    addEditableListeners(tags_map.get(type));
  }

  addDeleteAccountListeners(delete_tags);
}

/**
 * Add listeners to edit account modal windows.
 *
 * @param tags array of tag variables
 */
function addEditableListeners(tags) {
  let [modal, open, close] = tags;

  open.addEventListener("click", () => {
    showElement(modal);
  });

  close.addEventListener("click", () => {
    resetAccountFormAlert();
    hideElement(modal);
  });
}

/**
 * Add listeners to open and close the account deletion modal window.
 *
 * @param tags array of tag variables
 */
function addDeleteAccountListeners(tags) {
  let [modal, open, close] = tags;
  open.addEventListener("click", () => {
    showElement(modal);
  });
  close.addEventListener("click", () => {
    hideElement(modal);
  });
}

/************************************************
 * UPDATING USER INFO
 ************************************************/
const accountFormAlerts = document.querySelectorAll(".account__form--alert");
const userId = document.querySelector("#field_userId").innerHTML;

/**
 * Sets the alert on the form to the given alert message.
 *
 * @param alertMessage the message to set as alert.
 */
const setAccountFormAlert = function (alertMessage) {
  accountFormAlerts.forEach(alert => alert.innerHTML = `${alertMessage}`);
};

/**
 * Sets the alert on the form to empty.
 */
const resetAccountFormAlert = function () {
  accountFormAlerts.forEach(alert => alert.innerHTML = "");
};

/**
 * Sets alert to notify about wrong credentials.
 */
const updatePasswordError = function () {
  setAccountFormAlert("Wrong password")
}

/************************************************
 * UPDATING USER'S NAME
 ************************************************/

/**
 * Checks if the name form is valid.
 *
 * @return {boolean} true if valid, false if not.
 */
const isNameFormValid = function () {
  const firstName = document.querySelector(`input[name="edit_firstName"]`).value;
  const lastName = document.querySelector(`input[name="edit_lastName"]`).value;
  return firstName !== "" && lastName !== "";
};

/**
 * Returns an object of the user's updated name.
 *
 * @return {{firstName: *, lastName: *}}
 */
const getUpdatedName = function () {
  const firstName = document.querySelector(`input[name="edit_firstName"]`).value;
  const lastName = document.querySelector(`input[name="edit_lastName"]`).value;
  return {
    enabled: true,
    firstName: firstName,
    lastName: lastName,
  };
};

/**
 * Sends request to update name.
 */
const updateNameRequest = function (event) {
  event.preventDefault();
  if (isNameFormValid()) {
    sendApiRequest(`/users/${userId}`, "PUT", getUpdatedName(), reloadCurrentPage);
  } else {
    setAccountFormAlert("All fields need to be filled in");
  }
};

const submitName = document.querySelector(`input[name="changeName"]`);
submitName.addEventListener("click", updateNameRequest);

/************************************************
 * UPDATING USER'S EMAIL
 ************************************************/

/**
 * Checks if the email form is valid.
 *
 * @return {boolean} true if valid, false if not.
 */
const isEmailFormValid = function () {
  const email = document.querySelector(`input[name="edit_email"]`).value;
  const password = document.querySelector(`input[name="edit_email_password"]`).value;
  return email.match(/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/) && password !== '';
};

/**
 * Returns an object of the user's updated email and password.
 *
 * @return {{password: *, enabled: boolean, email: *}}
 */
const getUpdateEmailBody = function () {
  const email = document.querySelector(`input[name="edit_email"]`).value;
  const password = document.querySelector(`input[name="edit_email_password"]`).value;
  return {
    enabled: true,
    email: email,
    password: password,
  };
};

/**
 * Sends request to update email.
 */
const updateEmailRequest = function (event) {
  event.preventDefault();

  const updateEmailError = function () {
    setAccountFormAlert("Wrong password")
  }

  if (isEmailFormValid()) {
    sendApiRequest(`/users/${userId}`, "PUT", getUpdateEmailBody(), goToFrontpage, updateEmailError);
  } else {
    setAccountFormAlert("A valid email and password need to filled in");
  }
};

const changeEmailButton = document.querySelector(`input[name="changeEmail"]`);
changeEmailButton.addEventListener("click", updateEmailRequest);

/************************************************
 * UPDATING USER'S PASSWORD
 ************************************************/

/**
 * Checks if the new password form is valid.
 *
 * @return {boolean} true if valid, false if not.
 */
const isPasswordValid = function () {
  const password = document.querySelector(`input[name="edit_password"]`).value;
  const newPassword = document.querySelector(`input[name="edit_newPassword"]`).value;
  return password !== "" && newPassword !== "";
};

/**
 * Returns the old and new password as an object.
 *
 * @return {{password: *, newPassword: *}}
 */
const getUpdatedPassword = function () {
  const password = document.querySelector(`input[name="edit_password"]`).value;
  const newPassword = document.querySelector(`input[name="edit_newPassword"]`).value;

  return {
    enabled: true,
    password: password,
    newPassword: newPassword,
  };
};

/**
 * Sends request to update password.
 *
 * @param event
 */
const updatePasswordRequest = function (event) {
  event.preventDefault();
  if (isPasswordValid()) {
    sendApiRequest(
        `/users/${userId}`,
        "PUT",
        getUpdatedPassword(),
        reloadCurrentPage,
        updatePasswordError, updatePasswordError
    );
  } else {
    setAccountFormAlert("Please fill in your current and new password")
  }
};

const submitPassword = document.querySelector(`input[name="changePassword"]`);
submitPassword.addEventListener("click", updatePasswordRequest);

/************************************************
 * UPDATING ADDRESS
 ************************************************/

/**
 * Check if the address form is filled.
 *
 * @return {boolean} true if filled, false if not.
 */
const isAddressFormValid = function () {
  const addressLine = document.querySelector(
    `input[name="addressLine1"]`
  ).value;
  const municipality = document.querySelector(
    `input[name="municipality"]`
  ).value;
  const zipCode = document.querySelector(`input[name="zipCode"]`).value;
  const country = document.querySelector(`input[name="country"]`).value;
  return (
    addressLine !== "" &&
    municipality !== "" &&
    zipCode !== "" &&
    country !== ""
  );
};

/**
 * Returns an object of the user's updated address values.
 *
 * @return {{country, city, postalCode, addressLine1}}
 */
const getUpdatedAddress = function () {
  const requestBody = {
    enabled: true,
    addressLine1: document.querySelector(`input[name="addressLine1"]`).value,
    postalCode: document.querySelector(`input[name="zipCode"]`).value,
    city: document.querySelector(`input[name="municipality"]`).value,
    country: document.querySelector(`input[name="country"]`).value,
  };
  const addressLine2 = document.querySelector(
    `input[name="addressLine2"]`
  ).value;
  if (addressLine2 !== "" || addressLine2 !== undefined) {
    requestBody.addressLine2 = addressLine2;
  } else {
    requestBody.addressLine2 = "";
  }

  return requestBody;
};

/**
 * Creates an address string out of the given object.
 *
 * @param object object to create address string of.
 * @return {string} the object as an address string.
 */
const createAddressStringFromObject = function (object) {
  return `${object.addressLine1} ${object.postalCode} ${object.city} ${object.country}`;
};

/**
 * Sends request to update address.
 *
 * @return {Promise<void>}
 */
const updateAddressRequest = async function (event) {
  event.preventDefault();
  resetAccountFormAlert();

  const requestBody = getUpdatedAddress();
  const validAddress = await isAddressValid(createAddressStringFromObject(requestBody));

  if (isAddressFormValid()) {
    if (validAddress) {
      sendApiRequest(
        `/users/${userId}`,
        "PUT",
        requestBody,
        reloadCurrentPage
      );
    } else {
      setAccountFormAlert("Address doesn't exist");
    }
  } else {
    setAccountFormAlert("Please fill in required fields marked with *");
  }
};

const submitAddress = document.querySelector(`input[name="changeAddress"]`);
submitAddress.addEventListener("click", updateAddressRequest);

/************************************************
 * DELETING USER
 ************************************************/

/**
 * Returns the user's password and enabled: false as an object.
 *
 * @return {{password: *, enabled: boolean}}
 */
const getDeleteAccountBody = function () {
  const password = document.querySelector(`input[name="delete-account-password"]`).value;
  return {
    password: password,
    enabled: false,
  }
}

/**
 * Sends request to delete/disable the account.
 *
 * @param event
 */
const deleteAccountRequest = function(event) {
  event.preventDefault();

  sendApiRequest(`/users/${userId}`, "PUT", getDeleteAccountBody(), goToFrontpage);
}

const deleteAccountButton = document.querySelector(`input[name="deleteAccount"]`);
deleteAccountButton.addEventListener("click", deleteAccountRequest);

if (isAddressFormValid()) {
  getAddressInfo(createAddressStringFromObject(getUpdatedAddress())).then((data) => renderMap(data[0], data[1]));
}
