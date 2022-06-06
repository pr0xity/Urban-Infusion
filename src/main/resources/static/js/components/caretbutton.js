import { hideElement, showElement, isElementHidden } from "../tools.js";

/**
 * Buttons to toggle between hide and display for the heading underneath
 * a heading.
 */
export default class CaretButtons {
  buttonClass;
  headingClass;
  headings;

  /**
   * Creates an instance of caret buttons which will toggle hide and display
   * for the sibling below heading class.
   *
   * @param buttonClass the button class for the caret buttons to create.
   * @param headingClass the class of headings to create buttons for ("***__heading").
   */
  constructor(buttonClass, headingClass) {
    this.buttonClass = buttonClass;
    this.headingClass = headingClass;
    this.headings = document.body.querySelectorAll(`.${headingClass}`);
    this.ariaLabel = "show more";
  }

  /**
   * Creates caret buttons for the given list of headings.
   */
  createCaretButtons() {
    let index = 0;
    this.headings.forEach((heading) => {
      heading.insertAdjacentHTML(
        "beforeend",
        `<button class="${this.buttonClass}" aria-label="${this.ariaLabel}" data-item="${index}"><i class="ph-caret-left"></i></button>`
      );
      this.setCaretButtonToClosed(this.getButtonFromHeading(heading));
      index++;
    });
    this.addCaretButtonListeners();
  }

  /**
   * Iterates over buttons and adds event listeners to the buttons.
   */
  addCaretButtonListeners() {
    const caretButtons = document.body.querySelectorAll(`.${this.buttonClass}`);
    caretButtons.forEach((button) => {
      button.addEventListener("click", () => {
        if (!isElementHidden(this.getContentNodeFromButton(button))) {
          this.setCaretButtonToClosed(button);
        } else {
          this.setCaretButtonToOpen(button);
        }
      });
    });
  }

  /**
   * Removes the caret buttons from list of headings.
   */
  removeCaretButtons() {
    this.headings.forEach((heading) => {
      if (this.buttonExists(heading)) {
        showElement(this.getContentNodeFromHeading(heading));
        heading.removeChild(this.getButtonFromHeading(heading));
      }
    });
  }

  /**
   * Sets the given caret button to close, hiding the content node it represents
   * and rotates the button to closed position.
   *
   * @param button the caret button to set as closed.
   */
  setCaretButtonToClosed(button) {
    hideElement(this.getContentNodeFromButton(button));
    button.classList.remove("btn--rotated");
  }

  /**
   * Sets the given caret button to open, displaying the content node it represents
   * and rotates the button to open position.
   *
   * @param button the caret button to set as open.
   */
  setCaretButtonToOpen(button) {
    showElement(this.getContentNodeFromButton(button));
    button.classList.add("btn--rotated");
  }

  /**
   * Checks if the caret buttons exists with the given heading.
   * Returns true if the caret button exists, false if not.
   *
   * @param heading the heading to check if contains a caret button.
   * @returns true if button exist, false if not.
   */
  buttonExists(heading) {
    return heading.childNodes[1] !== undefined;
  }

  /**
   * Retrieves the node which the given heading represents.
   *
   * @param heading the heading to find content node of.
   * @returns the node the given heading represents.
   */
  getContentNodeFromHeading(heading) {
    return heading.parentNode.querySelector("[data-content]");
  }

  /**
   * Retrieves the node which the given caret button represents.
   *
   * @param button the caret button to find content node of,
   * @returns {Element} the node the given caret button represents.
   */
  getContentNodeFromButton(button) {
    return button.parentNode.parentNode.querySelector("[data-content]");
  }

  /**
   * Retrieves the button belonging to the given heading.
   *
   * @param heading heading to find the button of.
   * @returns the button of the given heading, undefined if not.
   */
  getButtonFromHeading(heading) {
    return heading.querySelector(`.${this.buttonClass}`);
  }
}
