// For REST API requests
const URL = "http://localhost:8080";
const FORGOTTEN_PASSWORD_API_PATHNAME = "/api/forgotten-password";
const AUTHENTICATION_API_PATHNAME = "/api/login";
const REGISTRATION_API_PATHNAME = "/api/register";
const WISHLIST_API_PATHNAME = "/api/wishlists";
const RATING_API_PATHNAME = "/api/ratings";
const PRODUCT_API_PATHNAME = "/api/products";
const PRODUCT_CATEGORY_API_PATHNAME = "/api/product-categories";
const USERS_API_PATHNAME = "/api/users";
const ORDERS_API_PATHNAME = "/api/orders";
const IMAGE_API_PATHNAME = "/api/products/images";
const CART_API_PATHNAME = "/api/carts";

// Page pathname.
const HOME_PATHNAME = "/";
const PRODUCT_PATHNAME = "/products";
const WISHLIST_PATHNAME = "/wishlist";

// Size for when to render mobile layout.
const mobileLayoutSize = window.matchMedia("(max-width: 54em)");
let map;
let marker;

/**
 * Go to frontpage
 */
const goToFrontpage = function () {
  window.location.href = HOME_PATHNAME;
};

/**
 * Reloads the current page.
 */
const reloadCurrentPage = function () {
  window.location.reload();
};

/**
 * Sends an API request to the tea shops backend API.
 *
 * @param pathname the request mapping (pathname not whole url) to send request to.
 * @param method request method.
 * @param body the request body to be sent, set as null if not needed.
 * @param successCallback method to do on status Ok.
 * @param unauthorizedCallback method to do on status 401 unauthorized, set as null if not needed.
 * @param errorCallback method to do on error.
 */
const sendApiRequest = function (pathname, method, body = null, successCallback = null, unauthorizedCallback = null, errorCallback = null) {

  /**
   * Returns fetch request with body and headers defined.
   *
   * @returns {Promise<Response>} the fetch request with body and headers.
   */
  const fetchWithBody = function () {
    return fetch(`${URL}${pathname}`, {
      method: method,
      body: JSON.stringify(body),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    });
  };

  /**
   * Returns fetch request with no body or headers.
   *
   * @returns {Promise<Response>} fetch request with no body or headers.
   */
  const fetchWithoutBody = function () {
    return fetch(`${URL}${pathname}`, {
      method: method,
    });
  };

  /**
   * Gets the appropriate fetch request according to defined parameters.
   *
   * @returns {Promise<Response>} fetch request fitting the parameters.
   */
  const getFetchRequest = function () {
    if (body != null) {
      return fetchWithBody();
    } else {
      return fetchWithoutBody();
    }
  };

  // send request and handle it.
  return getFetchRequest().then((response) => {
    if (response.ok) {
      const headers = response.headers.get("content-type");
      if (successCallback !== null) {
        successCallback();
        if (headers !== null && headers.includes("application/json")) {
          return response.json();
        }
      } else {
        console.log("Request was successful");
        if (headers !== null && headers.includes("application/json")) {
          return response.json();
        }
      }
    } else if (response.status === 401) {
      if (unauthorizedCallback !== null) unauthorizedCallback();
    } else if (errorCallback !== null) {
      errorCallback();
    } else {
      console.error("An error occurred, contact customer service.");
    }
  });
};

/**
 * Sends get request to the given url and parses the response as JSON.
 *
 * @param url
 * @param errorMsg
 * @returns {Promise<any>}
 */
const getJSON = function (url, errorMsg = "Something went wrong") {
  return fetch(url).then((response) => {
    if (!response.ok) throw new Error(`${errorMsg} (${response.status})`);

    return response.json();
  });
};

/**
 * Returns the product id from the data attribute on the element given.
 *
 * @param element the element to retrieve product id from.
 * @returns {string} the product id from the element.
 */
const getProductIdFromElement = function (element) {
  return element.dataset.productid;
};

/**
 * Creates an address query parameter to be used for geolocation API.
 *
 * @param address address to make query parameter out of.
 * @returns {string} the address as query parameter.
 */
const createAddressQueryParam = function (address) {
  return address.replaceAll(" ", "+").replaceAll(",", "");
};

/**
 * Sends get request for address information, if successful returns a promise
 * containing address information, if unsuccessful returns an error to console.
 *
 * @param address the address to get information about.
 * @returns {Promise<*[]>} promise containing address information.
 */
const getAddressInfo = async function (address) {
  const addressParams = createAddressQueryParam(address);

  const [data] = await getJSON(
    `https://nominatim.openstreetmap.org/search?q=${addressParams}&format=json`
  );
  try {
    const latitude = data.lat;
    const longitude = data.lon;
    const addressLine = data.display_name.split(",");
    const address = {
      addressLine1: `${addressLine[1]} ${addressLine[0]}`,
      city: `${addressLine[2]}`,
      postalCode: `${addressLine[4]}`,
      country: `${addressLine[5]}`,
    }
    return [latitude, longitude, address];
  } catch (e) {
    console.log(e);
  }
};

/**
 * Returns true if address is valid, false if not.
 *
 * @param address address to check for validity
 * @return {Promise<boolean>} true if valid, false if not.
 */
const isAddressValid = async function (address) {
  return await getAddressInfo(`${address}`).then((data) => {
    return data !== undefined;
  });
};

/**
 * Returns true if all the inputs in the given object is filled, false if not.
 *
 * @param addressInputs an object containing the address inputs (name is strict 'addressLine1, city, postalCode, country')
 * @return {boolean} true if all the inputs are filled, false if not.
 */
const isAddressFormValid = function (addressInputs) {
  const addressLine1 = addressInputs.addressLine1.value;
  const city = addressInputs.city.value;
  const postalCode = addressInputs.postalCode.value;
  const country = addressInputs.country.value;

  return (
    addressLine1 !== "" && city !== "" && postalCode !== "" && country !== ""
  );
};

/**
 * Finds address info from the given address and injects the info into the given fields.
 *
 * @param address the address to find address info from.
 * @param addressLine1 the address line 1 input field to inject info into,
 * @param city the city input field to inject info into.
 * @param postalCode the postal code input field to inject info into.
 * @param country the country input field to inject info into.
 */
const injectAddressIntoInputFields = function(address, addressLine1, city, postalCode, country) {
  getAddressInfo(address).then(addressInfo => {
    addressLine1.value = addressInfo[2].addressLine1;
    city.value = addressInfo[2].city;
    postalCode.value = addressInfo[2].postalCode;
    country.value = addressInfo[2].country;
  });
}

/**
 * Checks if the given email address has a valid format.
 *
 * @param {*} email the email address to check if it has a valid format.
 * @returns through if the email address is a valid format, false if not.
 */
const isEmailAddressValid = function (email) {
  const emailPattern = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
  return !!email.match(emailPattern) && email !== "";
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
 * Hides the given element.
 *
 * @param element element to be hidden.
 */
const hideElement = function (element) {
  element.classList.add("hidden");
};

/**
 * Displays the given element.
 *
 * @param element element to be displayed.
 */
const showElement = function (element) {
  element.classList.remove("hidden");
};

/**
 * Returns whether the element is hidden.
 *
 * @param element the element to check if is hidden.
 * @return {boolean} true if hidden, false if not.
 */
const isElementHidden = function (element) {
  return element.classList.contains("hidden");
};

/**
 * Initializes and renders the map to the given latitude and longitude.
 *
 * @param latitude the latitude to render the map to.
 * @param longitude the longitude to render the map to.
 */
const renderMap = function (latitude, longitude) {
  map = L.map("map").setView([latitude, longitude], 17);

  const leafIcon = new L.Icon({
    iconUrl: "../img/icons/mapmarker.svg",
    shadowUrl: "../img/icons/shadow.svg",
    iconSize: [41, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [61, 51],
  });

  marker = L.marker([latitude, longitude], { icon: leafIcon }).addTo(map);
  L.tileLayer(
    "https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png",
    {
      maxZoom: 20,
      attribution:
        '&copy; <a href="https://stadiamaps.com/">Stadia Maps</a>, &copy; <a href="https://openmaptiles.org/">OpenMapTiles</a> &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors',
    }
  ).addTo(map);
};

/**
 * Moves the map to a new location and places a marker.
 *
 * @param latitude the new latitude.
 * @param longitude the new longitude.
 */
const moveMap = function (latitude, longitude) {
  map.panTo([latitude, longitude], 17);

  const leafIcon = new L.Icon({
    iconUrl: "../img/icons/mapmarker.svg",
    shadowUrl: "../img/icons/shadow.svg",
    iconSize: [41, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [61, 51],
  });

  map.removeLayer(marker);
  L.marker([latitude, longitude], { icon: leafIcon }).addTo(map);
};
