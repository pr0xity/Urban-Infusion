/**
 * Sends an API request to the sites API URL.
 *
 * @param pathname the request mapping to send request to.
 * @param method request method.
 * @param successCallback method to do on status Ok.
 * @param unauthorizedCallback method to do on status 401 unauthorized.
 */
const sendApiRequest = function (pathname, method, successCallback, unauthorizedCallback, errorCallback) {
  return fetch(`${URL}${pathname}`, {
    method: method,
  }).then((response) => {
    if (response.ok) {
      successCallback();
    } else if (response.status === 401) {
      if (unauthorizedCallback !== undefined)
      unauthorizedCallback();
    } else if (errorCallback !== undefined) {
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
      `https://nominatim.openstreetmap.org/search?q=${addressParams}&format=json`);
  try {
    const latitude = data.lat;
    const longitude = data.lon;
    const address = data.display_name;
    return [latitude, longitude, address];
  } catch (e) {
    console.log(e);
  }
};