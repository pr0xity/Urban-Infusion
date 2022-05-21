/**
 * Represents a slider
 */
class Slider {
  slides;
  sliderContainer;
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
    this.sliderContainer = slides[0].parentNode;
    this.numberOfSlides = slides.length;
    this.currentSlide = Math.floor(this.numberOfSlides / 2);
    this.goToSlideReference = this.goToSlide.bind(this);
    this.touchStartHandlerReference = this.touchStartHandler.bind(this);
    this.touchEndHandlerReference = this.touchEndHandler.bind(this);
    this.formattingClasses = [
      "product-card__slide--secondary-prev",
      "product-card__slide--tertiary-prev",
      "product-card__slide--secondary-next",
      "product-card__slide--tertiary-next",
      "product-card__slide--active",
      "hidden",
    ];
  }

  /**
   * Creates slider controller, formats slides and adds events.
   */
  createSlides = function () {
    this.implementSliderController(this.sliderContainer);
    this.formatSlides();
    this.slides.forEach((slide) => {
      slide.addEventListener("click", this.goToSlideReference);
      this.addTouchEventsToSlider(slide);
    });
  };

  /**
   * Remove slide formatting, controller and events.
   */
  removeSlides() {
    this.removeFormat();
    this.removeSlideController();

    this.slides.forEach((slide) => {
      slide.removeEventListener("click", this.goToSlideReference);
      this.removeTouchEventsFromSlider(slide);
    });
  }

  /************************************************************
   * NAVIGATING SLIDER
   ************************************************************/

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

      hideElement(this.slides[nextSlide]);
      showElement(this.slides[this.currentSlide]);
      hideElement(this.slides[previousSlide]);
    }
    this.setActiveDot();
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

  /************************************************************
   * SLIDER CONTROLLER
   ************************************************************/

  /**
   * Adds slider controller to slider container and sets event listeners on the buttons.
   */
  implementSliderController = function () {
    this.sliderContainer.insertAdjacentHTML("afterbegin", this.createSliderControllerHtml());

    const sliderPrevBtn = this.sliderContainer.querySelector(".slider__btn--prev");
    const sliderNextBtn = this.sliderContainer.querySelector(".slider__btn--next");

    sliderPrevBtn.addEventListener("click", this.sliderPrev.bind(this));
    sliderNextBtn.addEventListener("click", this.sliderNext.bind(this));

    this.setActiveDot();
    this.dots.forEach((dot) => {
      dot.addEventListener("click", this.goToSlide.bind(this));
    });
  };

  /**
   * Creates the html for the slider controller
   *
   * @return {string} the html for slider controller as string
   */
  createSliderControllerHtml = function () {
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
    return controllerHtml;
  };

  /**
   * Removes the slider controllers from the slider
   */
  removeSlideController = function () {
    const slideController =
      this.sliderContainer.querySelectorAll(".slider-controls");
    slideController.forEach((controller) => {
      if (this.sliderContainer.contains(controller)) {
        controller.parentNode.removeChild(controller);
      }
    });
  };

  /**
   * Sets the current slide to the active dot.
   */
  setActiveDot = function () {
    this.dots = this.sliderContainer.querySelectorAll(".dots__dot");
    this.dots.forEach((dot) => dot.classList.remove("dots__dot--active"));
    this.dots[this.currentSlide].classList.add("dots__dot--active");
  };

  /**
   * Checks if the event target has a class from dots, returns true
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

  /************************************************************
   * TOUCH EVENTS ON SLIDER.
   ************************************************************/

  touchstart = 0;
  touchend = 0;

  /**
   * Adds touchend and touchstart event listeners to the slider container.
   */
  addTouchEventsToSlider = function (sliderContainer) {
    sliderContainer.addEventListener(
      "touchstart",
      this.touchStartHandlerReference
    );
    sliderContainer.addEventListener("touchend", this.touchEndHandlerReference);
  };

  /**
   * Removes the touchend and touchstart event listeners from the slider container.
   */
  removeTouchEventsFromSlider = function (sliderContainer) {
    sliderContainer.removeEventListener(
      "touchstart",
      this.touchStartHandlerReference
    );
    sliderContainer.removeEventListener(
      "touchend",
      this.touchEndHandlerReference
    );
  };

  /**
   * Sets the touchstart position to the touchstart variable.
   *
   * @param event touchstart event.
   */
  touchStartHandler = function (event) {
    this.touchstart = event.changedTouches[0].screenX;
  };

  /**
   * Figures out the direction of a touch event and
   * moves to the next slide accordingly.
   *
   * @param event the touchend event.
   */
  touchEndHandler = function (event) {
    this.touchend = event.changedTouches[0].screenX;

    if (this.swipedRight()) {
      this.sliderNext();
    }
    if (this.swipedLeft()) {
      this.sliderPrev();
    }
  };

  /**
   * Returns true if the touch event went right, false if not.
   *
   * @return {boolean} true if the touch event went right, false if not.
   */
  swipedRight = function () {
    return this.touchend < this.touchstart + 50;
  };

  /***
   * Returns true if the touch event went left, false if not.
   *
   * @return {boolean} true if the touch event went left, false if not.
   */
  swipedLeft = function () {
    return 50 + this.touchend > this.touchstart;
  };

  /************************************************************
   * SLIDES FORMATTING
   ************************************************************/

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
   * Formats the slides for three slides.
   */
  formatThreeSlides = function () {
    const previousSlide = (+this.currentSlide + 2) % this.numberOfSlides;
    const nextSlide = (+this.currentSlide + 1) % this.numberOfSlides;

    this.slides[previousSlide].classList.add(this.formattingClasses[0]);
    this.slides[nextSlide].classList.add(this.formattingClasses[2]);
  };

  /**
   * Formats the slides for four slides, where one is hidden.
   */
  formatFourSlides = function () {
    const previousSlide = (+this.currentSlide + 3) % this.numberOfSlides;
    const nextSlide = (+this.currentSlide + 1) % this.numberOfSlides;
    const hiddenSlide = (+this.currentSlide + 2) % this.numberOfSlides;

    this.slides[previousSlide].classList.add(this.formattingClasses[0]);
    this.slides[nextSlide].classList.add(this.formattingClasses[2]);
    hideElement(this.slides[hiddenSlide]);
  };

  /**
   * Formats the slides for five or more slides.
   */
  formatFiveOrMoreSlides = function () {
    this.slides.forEach((slide) => hideElement(slide));
    showElement(this.slides[this.currentSlide]);

    const slidePositions = [
      (+this.currentSlide + this.numberOfSlides - 1) % this.numberOfSlides,
      (+this.currentSlide + this.numberOfSlides - 2) % this.numberOfSlides,
      (+this.currentSlide + 1) % this.numberOfSlides,
      (+this.currentSlide + 2) % this.numberOfSlides,
    ];

    let i = 0;
    slidePositions.forEach((slidePosition) => {
      this.slides[slidePosition].classList.add(this.formattingClasses[i]);
      showElement(this.slides[slidePosition]);
      i++;
    });
  };
}
