// For REST API requests
const URL = "http://localhost:8080";
const HOME_PATHNAME = "/";
const AUTHENTICATION_API_PATHNAME = "/login";
const WISHLIST_API_PATHNAME = "/wishlist";
const RATING_API_PATHNAME = "/ratings";
const PRODUCT_PATHNAME = "/product/";

let map;
let marker;

/**
 * Go to frontpage
 */
const goToFrontpage = function () {
  window.location.href = HOME_PATHNAME;
}

/**
 * Reloads the current page.
 */
const reloadCurrentPage = function () {
  window.location.reload();
};

/**
 * Sends an API request to the sites API URL.
 *
 * @param pathname the request mapping to send request to.
 * @param method request method.
 * @param body the request body to be sent, set as null if not needed.
 * @param successCallback method to do on status Ok.
 * @param unauthorizedCallback method to do on status 401 unauthorized, set as null if not needed.
 * @param errorCallback method to do on error.
 */
const sendApiRequest = function (
  pathname,
  method,
  body,
  successCallback,
  unauthorizedCallback,
  errorCallback
) {
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
  getFetchRequest().then((response) => {
    if (response.ok) {
      successCallback();
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
    const address = data.display_name;
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
