"use strict";

const addToCartButtonClass = ".product__btn--add-to-cart";
const navCheckoutQuantity = document.querySelector(".nav__checkout--quantity");

/**
 * Implements functionality ot the add to cart buttons.
 */
const setAddToCartButtons = function () {

  /**
   * Gets the quantity of the shopping cart and display the
   * increment counter on the checkout button accordingly.
   */
  const setIncrementCounter = function () {
    getJSON(`${URL}${CART_API_PATHNAME}`).then((cartInfo) => {
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
   * Displays message to user to log in.
   */
  const addToCartUnauthorized = function () {
    userMenuButton.click();
    setLoginAlert("Log in to start shopping!");
  };

  /**
   * Increments the increment counter on the checkout buttons.
   */
  const addToCartSuccess = function () {
    sendApiRequest(`${CART_API_PATHNAME}`, "GET", null, setIncrementCounter);
  };

  /**
   * Sends a request to add the product whose add to cart button was pressed to the
   * user's shopping session/cart.
   */
  const sendAddToCartRequest = function (event) {
    const productId = getProductIdFromElement(
      event.target.closest(addToCartButtonClass)
    );

    /**
     * Sends a delete request for the product from the wishlist.
     */
    const removeProductFromWishlist = function () {
      sendApiRequest(
        `${WISHLIST_API_PATHNAME}/${productId}`,
        "DELETE",
        null,
        reloadCurrentPage,
        null,
        null
      );
    };

    if (window.location.pathname.includes(WISHLIST_API_PATHNAME)) {
      sendApiRequest(
        `${CART_API_PATHNAME}/${productId}`,
        "PUT",
        null,
        removeProductFromWishlist,
        null,
        null
      );
    } else {
      sendApiRequest(
        `${CART_API_PATHNAME}/${productId}`,
        "PUT",
        null,
        addToCartSuccess,
        addToCartUnauthorized,
        null
      );
    }
  };

  //Initializing.
  document.querySelectorAll(addToCartButtonClass).forEach((button) => {
    button.addEventListener("click", sendAddToCartRequest);
  });
  if (document.querySelectorAll(addToCartButtonClass)[0].dataset.loggedin === "true") {
    setIncrementCounter();
  }
};

setAddToCartButtons();