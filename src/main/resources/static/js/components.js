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
 *
 * Represents a slider
 */
class Slider {
    slides;
    element;
    currentSlide;
    numberOfSlides;
    dots;
    isFormatted = false;
    formattingClasses;

    /**
     * Creates an instance of slider with the given slides
     *
     * @param {*} slides
     */
    constructor(slides) {
        this.slides = slides;
        this.element = slides[0].parentNode;
        this.numberOfSlides = slides.length;
        this.currentSlide = Math.floor(this.numberOfSlides / 2);
        this.goToSlideReference = this.goToSlide.bind(this);
        this.formattingClasses = [
            "product-card__slide--active",
            "product-card__slide--secondary-next",
            "product-card__slide--secondary-prev",
            "product-card__slide--tertiary-next",
            "product-card__slide--tertiary-prev",
            "hidden",
        ];
    }

    /**
     * Creates slidecontroller, formats slides and adds events.
     */
    createSlides = function () {
        this.createSlideController();
        this.formatSlides();
        this.slides.forEach((slide) => {
            slide.addEventListener("click", this.goToSlideReference);
        });
    };

    /**
     * Formats the slides
     */
    formatSlides = function () {
        this.slides[this.currentSlide].classList.add("product-card__slide--active");
        if (this.numberOfSlides < 4) {
            this.formatThreeSlides();
        } else if (+this.numberOfSlides === 4) {
            this.formatFourSlides();
        } else if (this.numberOfSlides > 4) {
            this.formatFiveOrMoreSlides();
        }
        this.isFormatted = true;
    };

    /**
     * Remove slide formatting, controller and events.
     */
    removeSlides() {
        this.removeFormat();
        this.removeSlideController();
        this.slides.forEach((slide) => {
            slide.removeEventListener("click", this.goToSlideReference);
        });
    }

    /**
     * Removes format from the slides
     */
    removeFormat = function () {
        this.slides.forEach((slide) => {
            this.formattingClasses.forEach((formatClass) => {
                slide.classList.remove(formatClass);
            });
        });
    };

    /**
     * Creates the caret buttons and dots for slider controls.
     */
    createSlideController = function () {
        // Insert controller in HTML
        let controllerHtml = `<div class="slider-controls">
            <button aria-label="go to previous slide" class="slider__btn slider__btn--prev">
            <i class="ph-caret-left"></i>
            </button>
            <div class="dots">`;

        for (let dotIndex = 0; dotIndex < this.numberOfSlides; dotIndex++) {
            controllerHtml += `<button aria-label="got to this slide" class="dots__dot" data-slide="${dotIndex}"></button>`;
        }

        controllerHtml += `</div>
            <button aria-label="go to next slide"class="slider__btn slider__btn--next"><i class="ph-caret-right"></i></button>
            </div>`;

        this.getSliderContainer().insertAdjacentHTML("afterend", controllerHtml);

        const sliderPrevBtn = this.element.querySelector(".slider__btn--prev");
        const sliderNextBtn = this.element.querySelector(".slider__btn--next");

        sliderPrevBtn.addEventListener("click", this.sliderPrev.bind(this));
        sliderNextBtn.addEventListener("click", this.sliderNext.bind(this));

        this.setActiveDot();
        this.dots.forEach((dot) => {
            dot.addEventListener("click", this.goToSlide.bind(this));
        });
    };

    /**
     * Removes the slider controllers from the slider
     */
    removeSlideController = function () {
        const slideController = this.element.querySelectorAll(".slider-controls");
        slideController.forEach((controller) => {
            if (this.element.contains(controller)) {
                controller.parentNode.removeChild(controller);
            }
        });
    };

    /**
     * Goes to the next slide.
     *
     * @param {*} event event used for this handler
     */
    sliderNext = function (event) {
        if (+this.currentSlide === this.numberOfSlides - 1) {
            this.currentSlide = 0;
        } else {
            Number((this.currentSlide += 1));
        }

        this.goToSlide();
    };

    /**
     * Goes to the previous slide.
     *
     * @param {*} event event used for this handler
     */
    sliderPrev = function (event) {
        if (+this.currentSlide === 0) {
            this.currentSlide = this.numberOfSlides - 1;
        } else {
            this.currentSlide -= 1;
        }

        this.goToSlide();
    };

    /**
     * Sets the current slide to the active dot.
     */
    setActiveDot = function () {
        this.dots = this.element.querySelectorAll(".dots__dot");
        this.dots.forEach((dot) => dot.classList.remove("dots__dot--active"));
        this.dots[this.currentSlide].classList.add("dots__dot--active");
    };

    /**
     * Changes position for the slide.
     *
     * @param {*} event event used in this handler (only needed for dot)
     */
    goToSlide = function (event) {
        if (event !== undefined) {
            if (this.isEventFromSlide(event)) {
                this.currentSlide = Number(this.getSlideIndexFromSlide(event));
            } else if (this.isEventFromDot(event)) {
                this.currentSlide = Number(this.getSlideIndexFromDot(event));
            }
        }

        if (this.isFormatted) {
            this.removeFormat();
            this.formatSlides();
        } else {
            const previousSlide = (+this.currentSlide + 2) % this.numberOfSlides;
            const nextSlide = (+this.currentSlide + 1) % this.numberOfSlides;

            this.slides[nextSlide].classList.add("hidden");
            this.slides[this.currentSlide].classList.remove("hidden");
            this.slides[previousSlide].classList.add("hidden");
        }
        this.setActiveDot();
    };

    /**
     * Returns the slidercontainer for the slides.
     *
     * @returns slidercontainer for the slides.
     */
    getSliderContainer = function () {
        return this.slides[0].closest(`:not(${this.slides[0].classList[0]})`);
    };

    /**
     * Formats the slides for three slides.
     */
    formatThreeSlides = function () {
        // Slide positions
        const previousSlide = (+this.currentSlide + 2) % this.numberOfSlides;
        const nextSlide = (+this.currentSlide + 1) % this.numberOfSlides;

        // Formatting slides
        this.slides[previousSlide].classList.add(
            "product-card__slide--secondary-prev"
        );
        this.slides[nextSlide].classList.add("product-card__slide--secondary-next");
    };

    /**
     * Formats the slides for four slides, where one is hidden.
     */
    formatFourSlides = function () {
        //Slide positions
        const previousSlide = (+this.currentSlide + 3) % this.numberOfSlides;
        const nextSlide = (+this.currentSlide + 1) % this.numberOfSlides;
        const hiddenSlide = (+this.currentSlide + 2) % this.numberOfSlides;

        // Formatting slides
        this.slides[previousSlide].classList.add(
            "product-card__slide--secondary-prev"
        );
        this.slides[nextSlide].classList.add("product-card__slide--secondary-next");
        this.slides[hiddenSlide].classList.add("hidden");
    };

    /**
     * Formats the slides for five or more slides.
     */
    formatFiveOrMoreSlides = function () {
        this.slides.forEach((slide) => slide.classList.add("hidden"));
        this.slides[this.currentSlide].classList.remove("hidden");

        // Slide positions
        const secondaryPreviousSlide =
            (+this.currentSlide + this.numberOfSlides - 1) % this.numberOfSlides;
        const tertiaryPreviousSlide =
            (+this.currentSlide + this.numberOfSlides - 2) % this.numberOfSlides;
        const secondaryNextSlide = (+this.currentSlide + 1) % this.numberOfSlides;
        const tertiaryNextSlide = (+this.currentSlide + 2) % this.numberOfSlides;

        // Formatting slides
        this.slides[secondaryPreviousSlide].classList.add(
            "product-card__slide--secondary-prev"
        );
        this.slides[secondaryPreviousSlide].classList.remove("hidden");

        this.slides[tertiaryPreviousSlide].classList.add(
            "product-card__slide--tertiary-prev"
        );
        this.slides[tertiaryPreviousSlide].classList.remove("hidden");

        this.slides[secondaryNextSlide].classList.add(
            "product-card__slide--secondary-next"
        );
        this.slides[secondaryNextSlide].classList.remove("hidden");

        this.slides[tertiaryNextSlide].classList.add(
            "product-card__slide--tertiary-next"
        );
        this.slides[tertiaryNextSlide].classList.remove("hidden");
    };

    /**
     * Checks if the event target has a class from slides, returns true
     * if the event target is a slide, false if not.
     *
     * @param {*} event event to check the target for.
     * @returns true if event target is a slide, false if not.
     */
    isEventFromSlide = function (event) {
        return event.target.classList[0].includes(this.slides[0].classList[0]);
    };

    /**
     * Gets the slide index from an event target which is a slide.
     *
     * @param {*} event event to retrieve the slide index from
     * @returns slide index from slide.
     */
    getSlideIndexFromSlide = function (event) {
        return event.target.closest(`.${this.slides[0].classList[0]}`).dataset
            .slide;
    };

    /**
     * Checks if the event target has a class from fots, returns true
     * if the event target is a dot, false if not.
     *
     * @param {*} event event to check the target for.
     * @returns true if event target is a dot, false if not.
     */
    isEventFromDot = function (event) {
        return event.target.classList[0].includes("dots__dot");
    };

    /**
     * Gets the slide index from the corresponding dot.
     *
     * @param {*} event event to retrieve slide index for.
     * @returns slide index from dot.
     */
    getSlideIndexFromDot = function (event) {
        return event.target.dataset.slide;
    };
}