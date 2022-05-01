const URL = "http://localhost:8080"

/**
 * Login handling
 */
const loginbtn = document.querySelector("#login-btn");
const loginemail = document.querySelector("#login-email");
const loginpassword = document.querySelector("#login-password");

const sendLoginRequest = function (event) {
  if (event.type === "click" || event.key === "Enter") {
  fetch(`${URL}/login`, {
    method: "POST",
    body: JSON.stringify({
      email: loginemail.value.toString(),
      password: loginpassword.value.toString(),
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  }).then((response) => {
    console.log(response);
    if (response.ok) userMenuButton.click();
    window.location.reload();
  });
  }
};

if (loginbtn !== null) {
  loginbtn.addEventListener("click", sendLoginRequest);
  loginemail.addEventListener("keypress", sendLoginRequest)
  loginpassword.addEventListener("keypress", sendLoginRequest)
}

/**
 * Initializing common features for all pages.
 */
const tabletQuery = window.matchMedia("(max-width: 54em)");
const userMenuButton = document.querySelector("#user-menu");
const userMenuElement = document.querySelector(".nav__user-menu");
const navMobileButtons = document.querySelectorAll(".nav-mobile__btn");
const mobileMenuButtonOpen = document.querySelectorAll(".nav-mobile__btn")[0];
const mobileMenuButtonClose = document.querySelectorAll(".nav-mobile__btn")[1];
const navListElement = document.querySelector(".nav__list");
const navLinks = navListElement.querySelectorAll(".nav__link");
const overlayNavLinkMobileBtn = document.querySelector(
    ".nav__link-menu--overlay"
);
const overlayNavUserMobileBtn = document.querySelector(
    ".nav__user-menu--overlay"
);

const generalInitialize = function () {
  const footerCaretButtons = new CaretButtons("footer__btn", "footer-heading");

  const hideElement = function (element) {
    element.classList.add("hidden");
  }

  const showElement = function (element) {
    element.classList.remove("hidden");
  }

  const removeClickEventListenersFromList = function (list, functionReference) {
    list.forEach((item) => {
      item.removeEventListener("click", functionReference);
    });
  }

  const addClickEventListenersToList = function (list, functionReference) {
    list.forEach((item) => {
      item.addEventListener("click", functionReference);
    })
  }

  const clickMenuButtonClose = function () {
    mobileMenuButtonClose.click();
  };

  const clickUserMenuButton = function () {
    userMenuButton.click();
  }

  const navMobileButtonHandler = function () {
    mobileMenuButtonOpen.classList.toggle("hidden");
    mobileMenuButtonClose.classList.toggle("hidden");
    overlayNavLinkMobileBtn.classList.toggle("hidden");
    navListElement.classList.toggle("hidden");
    userMenuElement.classList.add("hidden");
    overlayNavUserMobileBtn.classList.add("hidden");
  }

  const userMenuButtonHandler = function () {
    userMenuElement.classList.toggle("hidden");
    overlayNavUserMobileBtn.classList.toggle("hidden");
  }

  if (tabletQuery.matches) {
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
tabletQuery.addEventListener("change", generalInitialize);
