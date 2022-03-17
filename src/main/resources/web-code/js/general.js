const userMenuBtn = document.querySelector("#user-menu");
const userMenuEl = document.querySelector(".nav__user-menu");
const tabletQuery = window.matchMedia("(max-width: 54em)");
const navMobileBtns = document.querySelectorAll(".nav-mobile__btn");
const navListEl = document.querySelector(".nav__list");

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
    let i = 0;
    this.elements.forEach((element) => {
      element.insertAdjacentHTML(
        "beforeend",
        `<button class="${this.btnClass}" data-item="${i}"><i class="ph-caret-left"></i></button>`
      );
      i++;
    });
    this.addCaretButtonListeners();
  };

  /**
   * Iterates over buttons and adds event listeners to the buttons.
   */
  addCaretButtonListeners = function () {
    this.elements.forEach((element) => {
      //if the elements button (childNodes[1]) is not undefined,
      //set the element sibling (parentNode.childNodes[3]) display to none.
      if (this.buttonExists(element)) {
        element.parentNode.childNodes[3].style.display = `none`;
      }
    });

    const btns = document.body.querySelectorAll(`.${this.btnClass}`);
    btns.forEach((button) => {
      const DEGREES_BUTTON_CLOSED = 0;
      const DEGREES_BUTTON_OPEN = -90;
      // Degrees closed
      let degrees = DEGREES_BUTTON_CLOSED;

      // Initial display value
      let display = "none";

      // On click hide or open list/text element
      button.addEventListener("click", (event) => {
        const clicked = event.target.closest(`.${this.btnClass}`);

        let item = clicked.dataset.item;

        if (degrees === 0) {
          // Degrees open
          degrees = DEGREES_BUTTON_OPEN;
          display = "initial";
        } else {
          degrees = DEGREES_BUTTON_CLOSED;
          display = "none";
        }

        clicked.style.transform = `rotate( ${degrees}deg)`;

        // Goes up to the parent node and gets the childnode 3 which is the element sibling to heading.
        this.elements[
          item
        ].parentNode.childNodes[3].style.display = `${display}`;
      });
    });
  };

  buttonExists = function (element) {
    return element.childNodes[1] !== undefined;
  };

  /**
   * Removes the caret buttons from the given list of elements.
   * @param {*} elements The list of elements to remove caret button from.
   */
  removeCaretBtns = function () {
    this.elements.forEach((element) => {
      if (this.buttonExists(element)) {
        element.parentNode.childNodes[3].style.display = `initial`;
        element.removeChild(element.childNodes[1]);
      }
    });
  };
}

const overlayNavLinkMobileBtn = document.querySelector(
  ".nav__link-menu--overlay"
);
const overlayNavUserMobileBtn = document.querySelector(
  ".nav__user-menu--overlay"
);
/**
 * If tablet query size matches creates the features and functions
 * for the components in tablet site.
 */
const init = function () {
  const footerCaretButtons = new CaretButtons("footer__btn", "footer-heading");

  if (tabletQuery.matches) {
    footerCaretButtons.createCaretBtns();
    navListEl.classList.add("hidden");
  } else {
    navMobileBtns[0].classList.remove("hidden");
    navMobileBtns[1].classList.add("hidden");
    overlayNavLinkMobileBtn.classList.add("hidden");
    overlayNavUserMobileBtn.classList.add("hidden");
    navListEl.classList.remove("hidden");
    footerCaretButtons.removeCaretBtns();
  }
};
document.body.classList.add("sticky");
navMobileBtns.forEach((btn) =>
  btn.addEventListener("click", (event) => {
    navMobileBtns[0].classList.toggle("hidden");
    navMobileBtns[1].classList.toggle("hidden");
    overlayNavLinkMobileBtn.classList.toggle("hidden");
    navListEl.classList.toggle("hidden");
    userMenuEl.classList.add("hidden");
    overlayNavUserMobileBtn.classList.add("hidden");
  })
);

overlayNavLinkMobileBtn.addEventListener("click", () => {
  navMobileBtns[1].click();
});

overlayNavUserMobileBtn.addEventListener("click", () => {
  userMenuBtn.click();
});

userMenuBtn.addEventListener("click", function (event) {
  userMenuEl.classList.toggle("hidden");
  overlayNavUserMobileBtn.classList.toggle("hidden");
});

// Update when the window is resized
tabletQuery.addEventListener("change", init);

init();
