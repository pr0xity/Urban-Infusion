/**********************************************
 * Initializing common features for all pages *
 **********************************************/
// Size for when to render mobile layout.
const mobileLayoutSize = window.matchMedia("(max-width: 54em)");
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
const navMobileElements = [mobileMenuButtonClose, mobileMenuButtonOpen, overlayNavLinkMenu, navListElement];
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
  const clickUserMenuButton = function () {
    userMenuButton.click();
  };

  /**
   * Displays and/or hides certain elements when navigation menu is clicked.
   */
  const navMobileButtonHandler = function () {
    navMobileElements.forEach(element => element.classList.toggle("hidden"));
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
  };

  /**
   * Sets/changes the element for mobile layout.
   */
  const setMobileLayout = function () {
    document.body.classList.add("sticky");
    footerCaretButtons.createCaretBtns();
    hideElement(navListElement);
    addClickEventListenersToList(navLinks, clickMenuButtonClose);

  }

  /**
   * Sets/changes the element for desktop layout.
   */
  const setDesktopLayout = function () {
    footerCaretButtons.removeCaretBtns();
    showElement(mobileMenuButtonOpen);
    hideElement(mobileMenuButtonClose);
    hideElement(overlayNavLinkMenu);
    showElement(navListElement);
    removeClickEventListenersFromList(navLinks, clickMenuButtonClose);

  }

  /**
   * Checks if screen size warrants mobile or desktop layout and changes accordingly.
   */
  const dynamicallyChangeSize = function() {
    if (mobileLayoutSize.matches) {
      setMobileLayout();
    } else {
      setDesktopLayout();
    }
  }

  // initial call to set mobile layout upon loading the site.
  dynamicallyChangeSize();
  mobileLayoutSize.addEventListener("change", dynamicallyChangeSize);


  addClickEventListenersToList(navMobileButtons, navMobileButtonHandler);
  overlayNavLinkMenu.addEventListener("click", clickMenuButtonClose);
  overlayNavUserMenu.addEventListener("click", clickUserMenuButton);
  userMenuButton.addEventListener("click", userMenuButtonHandler);
};

generalInitialize();