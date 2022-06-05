import {sendUpdateCartRequest, sendDeleteFromCartRequest, setIncrementCounter,} from "./controllers/cartcontroller.js";
import {
  getAddressInfo,
  getProductIdFromElement,
  hideElement,
  reloadCurrentPage,
  renderMap,
  showElement,
} from "./tools.js";
import {sendPostNewOrderRequest} from "./controllers/orderController.js";

/**
 * Implements listeners to input fields buttons and updates shopping session accordingly.
 */
const setCartItemControls = function () {
  let productId;
  let quantity;

  /**
   * Returns a request body for cart item requests.
   *
   * @param element
   * @return {{quantity, productId: string}}
   */
  const getCartItemRequestBody = function (element) {
    productId = getProductIdFromElement(element);
    quantity = element.value;
    return {
      productId: productId,
      quantity: quantity,
    };
  };

  /**
   * Sends request to update cart item to the new quantity.
   */
  const sendUpdateCartItemRequest = function (event) {
    event.preventDefault();
    const cartItemRequestBody = getCartItemRequestBody(event.target);
    if (quantity <= 0) {
      sendDeleteFromCartRequest(cartItemRequestBody.productId);
    } else {
      sendUpdateCartRequest(cartItemRequestBody);
    }
  };

  /**
   * Sends a delete request to delete the item with the given id from the shopping bag.
   */
  const sendCartItemDeleteRequest = function (event) {
    event.preventDefault();
    const productId = getProductIdFromElement(event.target.closest(".item__btn--delete"));
    sendDeleteFromCartRequest(productId).then(() => reloadCurrentPage());
  };

  /**
   * Adds change event listener to the given input field to send update cart item request.
   *
   * @param input the input field to add event listener to.
   */
  const addClickEventListenerToInputFields = function (input) {
    input.addEventListener("change", sendUpdateCartItemRequest);
  };

  /**
   * Adds click event listener to the given button to send delete request.
   *
   * @param button the button to add event listener to.
   */
  const addClickEventListenerToDeleteButtons = function (button) {
    button.addEventListener("click", sendCartItemDeleteRequest);
  };

  //Initializing.
  const itemQuantityInputs = document.querySelectorAll(".item__quantity");
  itemQuantityInputs.forEach((input) => {
    addClickEventListenerToInputFields(input);
  });

  const deleteButtons = document.querySelectorAll(".item__btn--delete");
  deleteButtons.forEach((button) => {
    addClickEventListenerToDeleteButtons(button);
  });
};

/**
 * Initializes functionality to send order requests.
 */
const setOrderRequestHandling = function () {
  const completeCheckoutButton = document.querySelector(
    ".checkout__btn--complete"
  );
  const completedCheckoutWindow = document.querySelector(".modal");
  const closeCompletedWindowButton = document.querySelector(".btn--close");
  const buttonText = document.querySelector("#complete-btn");
  const loadingAnimation = document.querySelector(".loading");

  /**
   * Displays the loading animation and hides the text from the button.
   */
  const displayLoadingAnimation = function () {
    showElement(loadingAnimation);
    hideElement(buttonText);
  };

  /**
   * Hides the loading animation and displays the text in the button.
   */
  const hideLoadingAnimation = function () {
    hideElement(loadingAnimation);
    showElement(buttonText);
  };

  /**
   * Show completed checkout modal if order was successful.
   */
  const orderRequestSuccess = function () {
    hideLoadingAnimation();
    document.querySelector(".checkout__list").replaceChildren();
    hideElement(completeCheckoutButton);
    showElement(completedCheckoutWindow);
    closeCompletedWindowButton.addEventListener("click", () => {
      hideElement(completedCheckoutWindow);
    });
  };

  /**
   * Sends an order request.
   */
  const sendOrderRequest = function (event) {
    event.preventDefault();
    displayLoadingAnimation();
    sendPostNewOrderRequest(orderRequestSuccess, hideLoadingAnimation, hideLoadingAnimation);
  };

  if (completeCheckoutButton !== null) {
    completeCheckoutButton.addEventListener("click", sendOrderRequest);
  }
};

// Initializing page.
let address = document.querySelector(".checkout__info--address").innerText;
getAddressInfo(address).then((data) => renderMap(data[0], data[1]));
setOrderRequestHandling();
setCartItemControls();
setIncrementCounter().finally();
