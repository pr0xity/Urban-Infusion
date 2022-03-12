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
  btns;
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
    this.caretButtonListeners();
  };

  /**
   * Iterates over footer buttons and adds event listeners to the buttons.
   */
  caretButtonListeners = function () {
    this.elements.forEach((element) => {
      if (element.childNodes[1] !== undefined) {
        element.parentNode.childNodes[3].style.display = `none`;
      }
    });

    this.btns = document.body.querySelectorAll(`.${this.btnClass}`);
    this.btns.forEach((button) => {
      // Initial degrees value
      let degrees = 0;

      // Initial display value
      let display = "none";

      // On click hide or open list/text element
      button.addEventListener("click", (event) => {
        const clicked = event.target.closest(`.${this.btnClass}`);

        let item = clicked.dataset.item;

        if (degrees === 0) {
          degrees = -90;
          display = "initial";
        } else {
          degrees = 0;
          display = "none";
        }

        clicked.style.transform = `rotate( ${degrees}deg)`;

        this.elements[
          item
        ].parentNode.childNodes[3].style.display = `${display}`;
      });
    });
  };

  /**
   * Removes the caret buttons from the given list of elements.
   *
   * @param {*} elements The list of elements to remove caret button from.
   */
  removeCaretBtns = function () {
    this.elements.forEach((element) => {
      if (element.childNodes[1] !== undefined) {
        element.parentNode.childNodes[3].style.display = `initial`;
        element.removeChild(element.childNodes[1]);
      }
    });
  };
}

/**
 * If tablet query size matches creates the features and functions
 * for the components in tablet site.
 */
const init = function () {
  const footerCaretButtons = new CaretButtons("footer__btn", "footer-heading");
  const overlayMobileBtn = document.querySelector(".overlay");

  document.body.classList.add("sticky");

  navMobileBtns.forEach((btn) =>
    btn.addEventListener("click", (event) => {
      navMobileBtns[0].classList.toggle("hidden");
      navMobileBtns[1].classList.toggle("hidden");
      overlayMobileBtn.classList.toggle("hidden");
      navListEl.classList.toggle("hidden");
      userMenuEl.classList.add("hidden");
    })
  );

  overlayMobileBtn.addEventListener("click", () => {
    navMobileBtns[1].click();
  });

  userMenuBtn.addEventListener("click", function (event) {
    userMenuEl.classList.toggle("hidden");
  });
  if (tabletQuery.matches) {
    footerCaretButtons.createCaretBtns();
    navListEl.classList.add("hidden");
  } else {
    navMobileBtns[0].classList.remove("hidden");
    navMobileBtns[1].classList.add("hidden");
    overlayMobileBtn.classList.add("hidden");
    navListEl.classList.remove("hidden");
    footerCaretButtons.removeCaretBtns();
  }
};

// Update when the window is resized
tabletQuery.addEventListener("change", init);

init();
