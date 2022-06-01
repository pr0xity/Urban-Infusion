let productId;

export const sendAddToCartRequest = function (event) {
  event.preventDefault();
  productId = getProductIdFromElement(event.target.closest(".product__btn--add-to-cart"));
  sendCartRequest(productId, "PUT")
};

export const sendUpdateCartRequest = function (event, body) {
  event.preventDefault();
  productId = body.productId;
  sendCartRequest(productId, "PUT", body)
}

export const sendDeleteFromCartRequest = function (event) {
  event.preventDefault();
  console.log(event.target.closest(".item__btn--delete"));
  productId = getProductIdFromElement(event.target.closest(".item__btn--delete"));
  sendCartRequest(productId, "DELETE", null, reloadCurrentPage)
}

function sendCartRequest(productId, method, body) {
  if (body !== undefined) {
    sendApiRequest(
      `${CART_API_PATHNAME}/${productId}`,
      `${method}`,
      body,
      updateCartItemSuccess,
      null,
      null
    ).finally();
  } else {
    sendApiRequest(
      `${CART_API_PATHNAME}/${productId}`,
      `${method}`,
      null,
      setIncrementCounter,
      null,
      null
    ).finally();
  }
}

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
  totalField.innerHTML = `${total}.0,-`;
};

/**
 * Gets the quantity of the shopping cart and display the
 * increment counter on the checkout button accordingly.
 */
export const setIncrementCounter = async function () {
  const navCheckoutQuantity = document.querySelector(".nav__checkout--quantity");
  await sendApiRequest(`${CART_API_PATHNAME}`, "GET", null, null, null, null)
    .then((cartInfo) => {
      const cartQuantity = cartInfo.quantity;
      if (cartQuantity > 0) {
        showElement(navCheckoutQuantity);
        navCheckoutQuantity.innerHTML = cartQuantity;
      } else {
        hideElement(navCheckoutQuantity);
      }
    });
};

/**
 * Retrieves and updates the new price for the cart item.
 */
const updateCartItemSuccess = async function () {
  await sendApiRequest(`${CART_API_PATHNAME}`, "GET", null, setIncrementCounter, null, null).then((cartInfo) => {
    const cartItems = cartInfo.cart;
    cartItems.forEach((item) => {
      if (item.product.id === Number(productId)) {
        setTotalFieldForCartItem(productId, item.total);
      }
    });
  });
};