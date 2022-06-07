import CaretButtons from "./components/caretbutton.js";
import { mobileLayoutSize, showElement, hideElement, isElementHidden } from "./tools.js";
import {forgottenPasswordEvent, loginEvent, resetLoginAlert, setLoginAlert} from "./views/loginMenuView.js";

/**********************************************
 * Sets header and footer for customer pages *
 **********************************************/
const loginEmail = document.querySelector("#login-email");
// Menu and close button for mobile layout.
const navMobileButtons = document.querySelectorAll(".nav-mobile__btn");
const mobileMenuButtonOpen = navMobileButtons[0];
const mobileMenuButtonClose = navMobileButtons[1];
// User menu, user-menu-button and user-menu-overlay.
const userMenuElement = document.querySelector(".nav__user-menu");
const userMenuButton = document.querySelector("#user-menu");
const overlayNavUserMenu = document.querySelector(".nav__user-menu--overlay");
// Nav lists, it's links and the overlay for mobile use.
const navListElement = document.querySelector(".nav__list");
const navLinks = navListElement.querySelectorAll(".nav__link");
const overlayNavLinkMenu = document.querySelector(".nav__link-menu--overlay");
const navMobileElements = [
  mobileMenuButtonClose,
  mobileMenuButtonOpen,
  overlayNavLinkMenu,
  navListElement,
];
// Links for wishlist and checkout which doesn't work when unauthorized.
const wishlistLink = document.querySelector("#wishlist-link");
const checkoutLink = document.querySelector("#checkout-link");

/**
 * Initializes common features on all pages.
 */
const generalInitialize = function () {
  const footerCaretButtons = new CaretButtons("footer__btn", "footer-heading");

  /**
   * Open and set the appropriate login alert message
   * if unauthorized attempt to access wishlist.
   */
  const wishlistUnauthorized = function () {
    userMenuButton.click();
    setLoginAlert("Log in to see your favourites");
  };

  if (wishlistLink !== null) {
    wishlistLink.addEventListener("click", wishlistUnauthorized);
  }

  /**
   * Open and set the appropriate login alert message
   * if unauthorized attempt to access checkout.
   */
  const checkoutUnauthorized = function () {
    userMenuButton.click();
    setLoginAlert("Log in to checkout");
  };

  if (checkoutLink !== null) {
    checkoutLink.addEventListener("click", checkoutUnauthorized);
  }

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
  const clickUserMenuButton = function (event) {
    event.preventDefault();
    userMenuButton.click();
  };

  /**
   * Displays and/or hides certain elements when navigation menu is clicked.
   */
  const navMobileButtonHandler = function () {
    navMobileElements.forEach((element) => element.classList.toggle("hidden"));
    hideElement(userMenuElement);
    hideElement(overlayNavUserMenu);
  };

  /**
   * Displays or hides elements when user menu is clicked.
   */
  const userMenuButtonHandler = function () {
    userMenuElement.classList.toggle("hidden");
    overlayNavUserMenu.classList.toggle("hidden");
    if (isElementHidden(userMenuElement)) {
      resetLoginAlert();
    }
    if (!isElementHidden(userMenuElement) && loginEmail !== null) {
      loginEmail.focus();
    }
  };

  /**
   * Sets/changes the element for mobile layout.
   */
  const setMobileLayout = function () {
    document.body.classList.add("sticky");
    footerCaretButtons.createCaretButtons();
    hideElement(navListElement);
    addClickEventListenersToList(navLinks, clickMenuButtonClose);
  };

  /**
   * Sets/changes the element for desktop layout.
   */
  const setDesktopLayout = function () {
    footerCaretButtons.removeCaretButtons();
    showElement(mobileMenuButtonOpen);
    hideElement(mobileMenuButtonClose);
    hideElement(overlayNavLinkMenu);
    showElement(navListElement);
    removeClickEventListenersFromList(navLinks, clickMenuButtonClose);
  };

  /**
   * Checks if screen size warrants mobile or desktop layout and changes accordingly.
   */
  const dynamicallyChangeSize = function () {
    if (mobileLayoutSize.matches) {
      setMobileLayout();
    } else {
      setDesktopLayout();
    }
  };
  const loginButton = document.querySelector("#login-btn");
  const forgottenPasswordButton = document.querySelector("#forgotten-password");

  //Adding event listeners if login button is present.
  if (loginButton !== null) {
    loginButton.addEventListener("click", loginEvent);
    forgottenPasswordButton.addEventListener("click", forgottenPasswordEvent);
  }

  // initial call to set mobile layout upon loading the site.
  dynamicallyChangeSize();
  mobileLayoutSize.addEventListener("change", dynamicallyChangeSize);
  document.querySelector("#sign-in-link").addEventListener("click", clickUserMenuButton);
  document.body.classList.add("sticky");
  addClickEventListenersToList(navMobileButtons, navMobileButtonHandler);
  overlayNavLinkMenu.addEventListener("click", clickMenuButtonClose);
  overlayNavUserMenu.addEventListener("click", clickUserMenuButton);
  userMenuButton.addEventListener("click", userMenuButtonHandler);
};

generalInitialize();
