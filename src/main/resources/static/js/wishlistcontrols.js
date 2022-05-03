/**********************************************
 * Wishlist controls *
 **********************************************/

const filledHeartIcon = `<i class="ph-heart-fill btn--icon btn--selected"></i>`;
const outlinedHeartIcon = `<i class="ph-heart-light btn--icon"></i>`;
const brokenHeartIcon = `<i class="ph-heart-break-light btn--icon"></i>`;
const wishlistButtonClass = ".product__btn--wishlist";

/**
 * Adds, formats and add requests to the wishlist buttons for the products on the page.
 */
const setWishlistButtons = function () {

  /**
   * Creates a button element for wishlist and adds the given attributes to it.
   *
   * @param dataWishlist "true" if it is in the wishlist, "false" if not.
   * @param dataProductId the product id.
   * @param ariaLabel aria-label of the buttons action.
   * @returns {HTMLButtonElement} the resulting button element.
   */
  const createWishlistButton = function (dataWishlist, dataProductId, ariaLabel) {
    const button = document.createElement("button");
    button.classList.add("product__btn--wishlist");
    button.dataset.wishlist = dataWishlist;
    button.dataset.id = dataProductId;
    button.ariaLabel = ariaLabel;
    return button;
  };

  /**
   * Returns whether the product belonging to the button is in the wishlist.
   *
   * @param button button containing data-wishlist.
   * @returns {boolean} "true" if it is in the wishlist, "false" if not.
   */
  const isProductInWishlist = function (button) {
    return button.dataset.wishlist === "true";
  };

  /**
   * Replaces current icon with a filled heart icon.
   *
   * @param button button to replace icon on.
   */
  const addFilledHeartIcon = function (button) {
    button.insertAdjacentHTML("afterbegin", filledHeartIcon);
  };

  /**
   * Replaces current icon with an outlined heart icon.
   *
   * @param button button to replace icon on.
   */
  const addOutlinedHeartIcon = function (button) {
    button.insertAdjacentHTML("afterbegin", outlinedHeartIcon);
  };

  /**
   * Replaces current icon with a broken heart icon.
   *
   * @param button button to replace icon on.
   */
  const addBrokenHeartIcon = function (button) {
    button.insertAdjacentHTML("afterbegin", brokenHeartIcon);
  };

  /**
   * Changes the heart icon on the given button to its opposite
   * (outlined heart / broken heart)
   * (filled heart / outlined heart)
   *
   * @param button the button to change icon of.
   */
  const addHeartIcon = function (button) {
    if (
      isProductInWishlist(button) &&
      window.location.pathname === "/wishlist"
    ) {
      addBrokenHeartIcon(button);
    } else if (isProductInWishlist(button)) {
      addFilledHeartIcon(button);
    } else if (!isProductInWishlist(button)) {
      addOutlinedHeartIcon(button);
    }
  };

  /**
   * Creates a remove from wishlist button and returns it.
   *
   * @param dataProductId the id of the product this button belongs to.
   * @returns {HTMLButtonElement} the remove from wishlist button element.
   */
  const removeFromWishlistButton = function (dataProductId) {
    const removeButton = createWishlistButton(
      "true",
      dataProductId,
      "Remove this item from your favourites"
    );
    addHeartIcon(removeButton);
    return removeButton;
  };

  /**
   * Creates an add to wishlist button and returns it.
   *
   * @param dataProductId the id of the product this button belongs to.
   * @returns {HTMLButtonElement} the add to wishlist button element.
   */
  const addToWishlistButton = function (dataProductId) {
    const addButton = createWishlistButton(
      "false",
      dataProductId,
      "Add this item to your favourites"
    );
    addHeartIcon(addButton);
    return addButton;
  };

  /**
   * Replaces the given wishlist button with another.
   *
   * @param wishlistButton the wishlist button to replace.
   * @param dataProductId the id of the product the button belongs to.
   */
  const replaceWishlistButton = function (wishlistButton, dataProductId) {
    if (isProductInWishlist(wishlistButton)) {
      wishlistButton.parentNode.replaceChild(
        removeFromWishlistButton(dataProductId),
        wishlistButton
      );
    } else if (!isProductInWishlist(wishlistButton)) {
      wishlistButton.parentNode.replaceChild(
        addToWishlistButton(dataProductId),
        wishlistButton
      );
    }
    addHeartIcon(wishlistButton);
  };

  /**
   * Sets wishlist buttons on pages with multiple productions.
   */
  const setWishlistButtonsOnMultipleProducts = function () {
    const products = document.querySelectorAll(".product-card");
    products.forEach((product) => {
      const dataProductId = product.dataset.id;
      const wishlistButton = product.querySelector(".product__btn--wishlist");
      replaceWishlistButton(wishlistButton, dataProductId);
    });
  };

  /**
   * Sets wishlist button on a product page.
   */
  const setWishlistButtonsOnAProductPage = function () {
    const product = document.querySelector(".product");
    const dataProductId = product.dataset.id;
    const wishlistButton = product.querySelector(".product__btn--wishlist");
    replaceWishlistButton(wishlistButton, dataProductId);
  };

  /**
   * Returns the product id from the button given.
   *
   * @param button the button to retrieve product id from.
   * @returns {string} the product id from the button.
   */
  const getProductIdFromButton = function (button) {
    return button.dataset.id;
  };

  /**
   * Sets data-wishlist attribute to true and returns the setWishlistButtons reference.
   *
   * @param button the add to wishlist button.
   * @returns {setWishlistButtons} function for setting the wishlist buttons.
   */
  const wishlistAddSuccessCallback = function (button) {
    button.dataset.wishlist = "true";
    return setWishlistButtons;
  };

  /**
   * Sets data-wishlist attribute to false and returns the setWishlistButtons reference.
   *
   * @param button the remove from wishlist button.
   * @returns {setWishlistButtons} function for setting the wishlist buttons.
   */
  const wishlistRemoveSuccessCallback = function (button) {
    button.dataset.wishlist = "false";
    return setWishlistButtons;
  };

  /**
   * Displays login menu with an alert.
   */
  const wishlistUnauthorizedCallback = function () {
    userMenuButton.click();
    setLoginAlert("Log in to save your favourites");
  };

  /**
   * Sends either a DELETE request or a POST request to add or remove a
   * product to or from the wishlist.
   *
   * @param event
   */
  const sendWishlistRequest = function (event) {
    const wishlistButton = event.target.closest(wishlistButtonClass);
    const productId = getProductIdFromButton(wishlistButton);

    if (isProductInWishlist(wishlistButton)) {
      sendApiRequest(
        `${WISHLIST_API_PATHNAME}/${productId}`,
        "DELETE",
        wishlistRemoveSuccessCallback(wishlistButton),
        wishlistUnauthorizedCallback
      );
    } else if (!isProductInWishlist(wishlistButton)) {
      sendApiRequest(
        `${WISHLIST_API_PATHNAME}/${productId}`,
        "POST",
        wishlistAddSuccessCallback(wishlistButton),
        wishlistUnauthorizedCallback
      );
    }
  };

  // Setting the buttons on the page
  if (
    window.location.pathname === "/" ||
    window.location.pathname === WISHLIST_API_PATHNAME
  ) {
    setWishlistButtonsOnMultipleProducts();
  } else if (window.location.pathname.includes(PRODUCT_PATHNAME)) {
    setWishlistButtonsOnAProductPage();
  }

  const wishlistButtons = document.querySelectorAll(wishlistButtonClass);

  //Adding event listeners
  if (wishlistButtons.length !== 0) {
    wishlistButtons.forEach((wishlistButton) => {
      wishlistButton.addEventListener("click", sendWishlistRequest);
    });
  }
};

const sendApiRequest = function (
  pathname,
  method,
  successCallback,
  unauthorizedCallback
) {
  fetch(`${URL}${pathname}`, {
    method: method,
  }).then((response) => {
    if (response.ok) {
      successCallback();
    } else if (response.status === 401) {
      unauthorizedCallback();
    } else {
      console.error("An error occurred, contact customer service.");
    }
  });
};

//Initial setting buttons.
setWishlistButtons();
