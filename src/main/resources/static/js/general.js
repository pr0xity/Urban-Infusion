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

/**
 * Buttons to toggle between hide and display for the element underneath
 * a heading.
 */
class CaretButtons {
  btnClass;
  elementsClass;
  elements;

  /**
   * Creates an instance of caret buttons which will toggle hide and display
   * for the sibling below heading class.
   *
   * @param {*} btnClass the class to add to the buttons
   * @param {*} elementsClass the class of elements to create buttons for ("***__heading").
   */
  constructor(btnClass, elementsClass) {
    this.btnClass = btnClass;
    this.elementsClass = elementsClass;
    this.elements = document.body.querySelectorAll(`.${elementsClass}`);
  }

  /**
   * Creates caret buttons for the given list of elements.
   */
  createCaretBtns = function () {
    let index = 0;
    this.elements.forEach((element) => {
      element.insertAdjacentHTML(
        "beforeend",
        `<button class="${this.btnClass}" data-item="${index}"><i class="ph-caret-left"></i></button>`
      );
      index++;
    });
    this.addCaretButtonListeners();
  };

  /**
   * Iterates over buttons and adds event listeners to the buttons.
   */
  addCaretButtonListeners = function () {
    this.elements.forEach((element) => {
      if (this.buttonExists(element)) {
        this.getContentNodes(element).style.display = `none`;
      }
    });

    const btns = document.body.querySelectorAll(`.${this.btnClass}`);
    btns.forEach((button) => {
      const DEGREES_BUTTON_CLOSED = 0;
      const DEGREES_BUTTON_OPEN = -90;

      let degrees = DEGREES_BUTTON_CLOSED;

      let display = "none";

      button.addEventListener("click", (event) => {
        const clicked = event.target.closest(`.${this.btnClass}`);
        let item = clicked.dataset.item;

        if (degrees === DEGREES_BUTTON_CLOSED) {
          degrees = DEGREES_BUTTON_OPEN;
          display = "initial";
        } else {
          degrees = DEGREES_BUTTON_CLOSED;
          display = "none";
        }

        clicked.style.transform = `rotate( ${degrees}deg)`;

        // Goes up to the parent node and gets the childnode 3 which is the element sibling to heading.
        this.getContentNodes(this.elements[item]).style.display = `${display}`;
      });
    });
  };

  /**
   * Removes the caret buttons from the given list of elements.
   * @param {*} elements The list of elements to remove caret button from.
   */
  removeCaretBtns = function () {
    this.elements.forEach((element) => {
      if (this.buttonExists(element)) {
        this.getContentNodes(element).style.display = `initial`;
        element.removeChild(this.getButtonFromElement(element));
      }
    });
  };

  /**
   * Checks if the caret buttons exists with the given element.
   * Returns true if the caret butten exists, false if not.
   *
   * @param {*} element the element to check if contains a caret button.
   * @returns true if button exist, false if not.
   */
  buttonExists = function (element) {
    return element.childNodes[1] !== undefined;
  };

  /**
   * Retrieves the element (content nodes) which are hidden/displayed with the caret buttons.
   *
   * @param {*} element header element for the corresponding content node.
   * @returns the content node belonging to the given element.
   */
  getContentNodes = function (element) {
    return element.parentNode.querySelector("[data-content]");
  };

  /**
   * Retrieves the button belonging to the given element.
   *
   * @param {*} element element to find the button of.
   * @returns the button of the given element, undefined if not.
   */
  getButtonFromElement = function (element) {
    return element.querySelector(`.${this.btnClass}`);
  };
}

/**
 * If tablet query size matches creates the features and functions
 * for the components in tablet site.
 */
const init = function () {
  const footerCaretButtons = new CaretButtons("footer__btn", "footer-heading");

  if (tabletQuery.matches) {
    footerCaretButtons.createCaretBtns();
    navListElement.classList.add("hidden");
    navLinks.forEach((link) => {
      link.addEventListener("click", closeMenuAfterLinkClick);
    });
  } else {
    mobileMenuButtonOpen.classList.remove("hidden");
    mobileMenuButtonClose.classList.add("hidden");
    overlayNavLinkMobileBtn.classList.add("hidden");
    // overlayNavUserMobileBtn.classList.add("hidden");
    navListElement.classList.remove("hidden");
    footerCaretButtons.removeCaretBtns();
    navLinks.forEach((link) => {
      link.removeEventListener("click", closeMenuAfterLinkClick);
    });
  }
};

document.body.classList.add("sticky");
navMobileButtons.forEach((btn) =>
  btn.addEventListener("click", (event) => {
    mobileMenuButtonOpen.classList.toggle("hidden");
    mobileMenuButtonClose.classList.toggle("hidden");
    overlayNavLinkMobileBtn.classList.toggle("hidden");
    navListElement.classList.toggle("hidden");
    userMenuElement.classList.add("hidden");
    overlayNavUserMobileBtn.classList.add("hidden");
  })
);

closeMenuAfterLinkClick = function () {
  mobileMenuButtonClose.click();
};

overlayNavLinkMobileBtn.addEventListener("click", () => {
  mobileMenuButtonClose.click();
});

overlayNavUserMobileBtn.addEventListener("click", () => {
  userMenuButton.click();
});

userMenuButton.addEventListener("click", function (event) {
  userMenuElement.classList.toggle("hidden");
  // if (tabletQuery.matches) {
  overlayNavUserMobileBtn.classList.toggle("hidden");
  // }
});

// Update when the window is resized
tabletQuery.addEventListener("change", init);

window.addEventListener("load", init);

const loginbtn = document.querySelector("#login-btn");
const loginemail = document.querySelector("#login-email");
const loginpassword = document.querySelector("#login-password");
const loginHandler = function (event) {
  console.log(event);
  if (event.type)
  fetch("http://localhost:8080/login", {
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
};

if (loginbtn !== null) {
  loginbtn.addEventListener("click", loginHandler);
  //loginemail.addEventListener("keypress", loginHandler)
}

/*const signout = document.querySelector("#signout");

const signoutHandler = function () {
  fetch("http://localhost:8080/logout", {
    method: "POST",
  }).then((response) => {
    console.log(response)
    if (response.ok) userMenuButton.click();
    //window.location.reload();
  });
};

if (signout !== null) {
  signout.addEventListener("click", signoutHandler);
}*/
