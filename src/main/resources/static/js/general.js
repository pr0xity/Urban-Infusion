const URL = "http://localhost:8080";
const AUTHENTICATION_API_PATHNAME = "/login";
const WISHLIST_API_PATHNAME = "/wishlist";
const PRODUCT_PATHNAME = "/product/";

/**********************************************
 * Log in handling *
 **********************************************/

const loginButton = document.querySelector("#login-btn");
const loginEmail = document.querySelector("#login-email");
const loginPassword = document.querySelector("#login-password");
const loginAlert = document.querySelector(".login__alert");

/**
 * Sets the login alert to the given message.
 *
 * @param alertMessage message to be set.
 */
const setLoginAlert = function (alertMessage) {
  if (loginButton !== null) {
    loginAlert.innerHTML = `${alertMessage}`;
  }
};

/**
 * Sets the login alert to empty.
 */
const resetLoginAlert = function () {
  if (loginButton !== null) {
    loginAlert.innerHTML = "";
  }
};

/**
 * Sends a POST request for log in.
 */
const sendLoginRequest = function (event) {
  if (event.type === "click" || event.key === "Enter") {
    fetch(`${URL}${AUTHENTICATION_API_PATHNAME}`, {
      method: "POST",
      body: JSON.stringify({
        email: loginEmail.value.toString(),
        password: loginPassword.value.toString(),
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    }).then((response) => {
      console.log(response);
      if (response.ok) {
        userMenuButton.click();
        window.location.reload();
      } else {
        setLoginAlert("Wrong username or password");
      }
    });
  }
};

//Adding event listeners if login button is present.
if (loginButton !== null) {
  loginButton.addEventListener("click", sendLoginRequest);
  loginEmail.addEventListener("keypress", sendLoginRequest);
  loginPassword.addEventListener("keypress", sendLoginRequest);
}

/**********************************************
 * Initializing common features for all pages *
 **********************************************/

const mobileLayoutSize = window.matchMedia("(max-width: 54em)");
const mobileMenuButtonOpen = document.querySelectorAll(".nav-mobile__btn")[0];
const mobileMenuButtonClose = document.querySelectorAll(".nav-mobile__btn")[1];
const userMenuButton = document.querySelector("#user-menu");
const userMenuElement = document.querySelector(".nav__user-menu");
const navMobileButtons = document.querySelectorAll(".nav-mobile__btn");
const navListElement = document.querySelector(".nav__list");
const navLinks = navListElement.querySelectorAll(".nav__link");
const wishlistLink = document.querySelector("#wishlist-link");
const checkoutLink = document.querySelector("#checkout-link");
const overlayNavLinkMobileBtn = document.querySelector(
  ".nav__link-menu--overlay"
);
const overlayNavUserMobileBtn = document.querySelector(
  ".nav__user-menu--overlay"
);

const getWishlistUnauthorized = function () {
  userMenuButton.click();
  setLoginAlert("Log in to see your favourites")
}

if (wishlistLink !== null) {
  wishlistLink.addEventListener("click", getWishlistUnauthorized);
}

const getCheckoutUnauthorized = function () {
  userMenuButton.click();
  setLoginAlert("Log in to checkout")
}

if (checkoutLink !== null) {
  checkoutLink.addEventListener("click", getCheckoutUnauthorized);
}

/**
 * Initializes common features on all pages.
 */
const generalInitialize = function () {
  const footerCaretButtons = new CaretButtons("footer__btn", "footer-heading");

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
   * Removes click event from the items in given list with the given function reference.
   *
   * @param list list with items to remove click event from.
   * @param functionReference function reference to remove from click event.
   */
  const removeClickEventListenersFromList = function (list, functionReference) {
    list.forEach((item) => {
      item.removeEventListener("click", functionReference);
    });
  };

  /**
   * Adds click event to the items in the given list with the given function reference.
   *
   * @param list list with items to add click event to.
   * @param functionReference function reference to add to click event.
   */
  const addClickEventListenersToList = function (list, functionReference) {
    list.forEach((item) => {
      item.addEventListener("click", functionReference);
    });
  };

  /**
   * Clicks on the close menu button.
   */
  const clickMenuButtonClose = function () {
    mobileMenuButtonClose.click();
  };

  /**
   * Clicks on the user menu button.
   */
  const clickUserMenuButton = function () {
    userMenuButton.click();
  };

  /**
   * Displays and/or hides certain elements when navigation menu is clicked.
   */
  const navMobileButtonHandler = function () {
    mobileMenuButtonOpen.classList.toggle("hidden");
    mobileMenuButtonClose.classList.toggle("hidden");
    overlayNavLinkMobileBtn.classList.toggle("hidden");
    navListElement.classList.toggle("hidden");
    userMenuElement.classList.add("hidden");
    overlayNavUserMobileBtn.classList.add("hidden");
  };

  /**
   * Displays or hides elements when user menu is clicked.
   */
  const userMenuButtonHandler = function () {
    userMenuElement.classList.toggle("hidden");
    overlayNavUserMobileBtn.classList.toggle("hidden");
    if (userMenuElement.classList.contains("hidden")) {
      resetLoginAlert();
    }
  };

  // Checks screen size and changes layout accordingly
  if (mobileLayoutSize.matches) {
    footerCaretButtons.createCaretBtns();
    hideElement(navListElement);
    navLinks.forEach((link) => {
      link.addEventListener("click", clickMenuButtonClose);
    });
  } else {
    //overlayNavUserMobileBtn.classList.add("hidden");
    mobileMenuButtonOpen.classList.remove("hidden");
    hideElement(mobileMenuButtonClose);
    hideElement(overlayNavLinkMobileBtn);
    showElement(navListElement);
    footerCaretButtons.removeCaretBtns();
    removeClickEventListenersFromList(navLinks, clickMenuButtonClose);
  }

  document.body.classList.add("sticky");
  addClickEventListenersToList(navMobileButtons, navMobileButtonHandler);
  overlayNavLinkMobileBtn.addEventListener("click", clickMenuButtonClose);
  overlayNavUserMobileBtn.addEventListener("click", clickUserMenuButton);
  userMenuButton.addEventListener("click", userMenuButtonHandler);
};

generalInitialize();

// Update when the window is resized
mobileLayoutSize.addEventListener("change", generalInitialize);
