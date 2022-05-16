const tag_types = ["email", "password", "address"];
const tags_array = ["modal", "open", "close", "field", "edit"];
const tags_map = createTagsMap();

const delete_tags = [
  document.getElementById("modal_delete"),
  document.querySelector(".account-info__btn--delete"),
  document.getElementById("close_delete"),
];

const forms = [
  document.querySelector(".modal__form--email"),
  document.querySelector(".modal__form--address"),
  document.querySelector(".modal__form--password"),
  document.querySelector(".modal__form--delete"),
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

  for (const form of forms) {
    addFormListener(form);
  }

  addDeleteAccountListeners(delete_tags);
}

/**
 * Add listeners to edit account modal windows.
 *
 * @param tags array of tag variables
 */
function addEditableListeners(tags) {
  let [modal, open, close, field, edit] = tags;
  open.addEventListener("click", () => {
    modal.classList.add("show");
    // Refactor this
    if (modal.classList.contains("account-info__change--address")) {
      document
        .querySelector(".account-info__change--overlay")
        .classList.add("show");
    }
  });
  close.addEventListener("click", () => {
    resetAccountFormAlert();
    modal.classList.remove("show");
    // Refactor this
    if (modal.classList.contains("account-info__change--address")) {
      document
        .querySelector(".account-info__change--overlay")
        .classList.remove("show");
    }
  });
  function updateField(event) {
    //     field.textContent = event.target.value;
  }
  //edit.addEventListener('change', updateField);
}

/**
 * Add listeners for form events.
 *
 * @param form the modal form in question
 */
function addFormListener(form) {
  /*form.addEventListener("submit", (e) => {
    e.preventDefault();
  });*/
}

/**
 * Add listeners to open and close the account deletion modal window.
 *
 * @param tags array of tag variables
 */
function addDeleteAccountListeners(tags) {
  let [modal, open, close] = tags;
  open.addEventListener("click", () => {
    modal.classList.add("show");
  });
  close.addEventListener("click", () => {
    modal.classList.remove("show");
  });
}

const accountFormAlert = document.querySelector(".account__form--alert");
// Initial retrieval for map rendering
let addressLine = document.querySelector(`input[name="addressLine1"]`).value;
let municipality = document.querySelector(`input[name="municipality"]`).value;
let zipCode = document.querySelector(`input[name="zipCode"]`).value;
let country = document.querySelector(`input[name="country"]`).value;
let address = `${addressLine} ${municipality} ${zipCode} ${country}`;
// Render map.
getAddressInfo(address).then((data) => renderMap(data[0], data[1]));

/**
 * Sets the alert on the form to the given alert message.
 *
 * @param alertMessage the message to set as alert.
 */
const setAccountFormAlert = function (alertMessage) {
  accountFormAlert.innerHTML = `${alertMessage}`;
};

/**
 * Sets the alert on the form to empty.
 */
const resetAccountFormAlert = function () {
  accountFormAlert.innerHTML = "";
};

/**
 * Check if the address form is filled.
 *
 * @param addressLine the first address-line form the form.
 * @param municipality the municipality form the form.
 * @param zipCode the zip code from the form.
 * @param country the country from the form.
 * @return {boolean} true if filled, false if not.
 */
const isAddressFormValid = function (addressLine, municipality, zipCode, country) {
  return (addressLine !== "" && municipality !== "" && zipCode !== "" && country !== "");
};

/**
 * Sends request to update address.
 *
 * @return {Promise<void>}
 */
const updateAddressRequest = async function () {
  resetAccountFormAlert();
  // Get updated values.
  addressLine = document.querySelector(`input[name="addressLine1"]`).value;
  municipality = document.querySelector(`input[name="municipality"]`).value;
  zipCode = document.querySelector(`input[name="zipCode"]`).value;
  country = document.querySelector(`input[name="country"]`).value;
  address = `${addressLine} ${municipality} ${zipCode} ${country}`;

  const validAddress = await isAddressValid(address);
  if (isAddressFormValid(addressLine, municipality, zipCode, country)) {
    if (validAddress) {
      //TODO: Send update request.
      getAddressInfo(address).then((data) => moveMap(data[0], data[1]));
    } else {
      setAccountFormAlert("Address doesn't exist");
    }
  } else {
    setAccountFormAlert("Please fill in required fields");
  }
};

const submitAddress = document.querySelector(`input[name="changeAddress"]`);
submitAddress.addEventListener("click", updateAddressRequest);
