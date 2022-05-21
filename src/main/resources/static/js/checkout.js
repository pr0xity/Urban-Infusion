"use strict";

const open_modal = document.querySelector(".open_modal");
const checkout_container = document.querySelector(".checkout__complete");
const checkout_overlay = document.querySelector(".checkout__complete--overlay");
const close_modal = document.querySelector(".checkout__btn--close");
const address = document.querySelector(".checkout__info--address").innerText;

addListeners();

function addListeners() {
  open_modal.addEventListener("click", () => {
    checkout_container.classList.add("show");
    checkout_overlay.classList.add("show");
  });
  close_modal.addEventListener("click", () => {
    checkout_container.classList.remove("show");
    checkout_overlay.classList.remove("show");
  });
}

//Render map
getAddressInfo(address).then((data) => renderMap(data[0], data[1]));

/**
 * Implements listeners to input fields buttons and updates shopping session accordingly.
 */
const setCartItemControls = function () {
  let productId;
  let quantity;

  /**
   * Sets the total field of the product with the given product id to the given total value.
   *
   * @param productId the product id of the product to update total price for.
   * @param total the total value to set for the current product id.
   */
  const setTotalFieldForCartItem = function (productId, total) {
    const totalFields = document.querySelectorAll(`.item__price`);
    let totalField;
    totalFields.forEach((field) => {
      if (getProductIdFromElement(field) === productId) {
        totalField = field;
      }
    });
    totalField.innerHTML = `${total},-`;
  };

  /**
   * Retrieves and updates the new price for the cart item.
   */
  const updateCartItemSuccess = function () {
    getJSON(`${URL}${CART_API_PATHNAME}`).then((cartInfo) => {
      const cartItems = cartInfo.cart;
      cartItems.forEach((item) => {
        if (item.product.id === Number(productId)) {
          setTotalFieldForCartItem(productId, item.total);
        }
      });
    });
  };

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
    const cartItemRequestBody = getCartItemRequestBody(event.target);
    if (quantity <= 0) {
      sendCartItemDeleteRequest(productId);
    }
    sendApiRequest(
      `${CART_API_PATHNAME}/${productId}`,
      "PUT",
      cartItemRequestBody,
      updateCartItemSuccess,
      null,
      null
    );
  };

  /**
   * Sends a delete request to delete the item with the given id from the shopping bag.
   */
  const sendCartItemDeleteRequest = function (event) {
    const cartItemRequestBody = getCartItemRequestBody(
      event.target.closest(".item__btn--delete")
    );
    sendApiRequest(
      `${CART_API_PATHNAME}/${cartItemRequestBody.productId}`,
      "DELETE",
      null,
      reloadCurrentPage,
      null,
      null
    );
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

setCartItemControls();
