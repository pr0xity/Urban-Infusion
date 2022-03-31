const headerEl = document.querySelector(".header");
const sectionHeroEl = document.querySelector(".section-hero");

/**
 * Adds sticky navigation when scrolled passed hero section.
 *
 * @param {*} entries
 */
const stickyNavigation = function (entries) {
  const entry = entries[0];

  if (entry.isIntersecting === false) {
    document.body.classList.add("sticky");
  }

  if (entry.isIntersecting === true) {
    document.body.classList.remove("sticky");
  }
};

const options = {
  root: null,
  threshold: 0,
  rootMargin: "190px",
};

const observer = new IntersectionObserver(stickyNavigation, options);

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

/**
 * Slider gallery for the static gallery on the landing page.
 */
class GallerySlider extends Slider {
  companyEl = document.querySelector(".company-grid");

  /**
   * Creates an instance of gallery slider
   *
   * @param {*} slides the slide to create a slider from.
   */
  constructor(slides) {
    super(slides);
  }

  /**
   * Creates a slider out of the gallery.
   */
  createSlides = function () {
    this.createSlideController();
    const galleries = document.querySelectorAll(".gallery");
    galleries[0].insertAdjacentHTML(
      "beforebegin",
      `<div class="slider-container">`
    );
    const sliderContainer = document.querySelector(".slider-container");
    sliderContainer.appendChild(galleries[0]);
    sliderContainer.appendChild(galleries[1]);
    sliderContainer.appendChild(galleries[2]);
    galleries[0].classList.add("hidden");
    galleries[2].classList.add("hidden");
  };

  /**
   * Appends gallery back to company section and removes
   * slidecontainer.
   */
  removeSlides = function () {
    const slideContainer = document.querySelector(".slider-container");
    const galleries = document.querySelectorAll(".gallery");
    if (slideContainer !== null) {
      this.companyEl.appendChild(galleries[0]);
      this.companyEl.appendChild(galleries[1]);
      this.companyEl.appendChild(galleries[2]);
      this.companyEl.removeChild(slideContainer);
      galleries.forEach((gallery) => gallery.classList.remove("hidden"));
    }
    this.removeSlideController();
  };

  /**
   * Removes the slider controllers from the slider
   */
  removeSlideController = function () {
    const slideController = this.companyEl.querySelectorAll(".slider-controls");
    slideController.forEach((controller) => {
      if (this.companyEl.contains(controller)) {
        this.companyEl.removeChild(controller);
      }
    });
  };
}

const featuredSlides = document.querySelectorAll(".featured-card");
const featuredSlider = new Slider(featuredSlides);
const gallerySlides = document.querySelectorAll(".gallery");
const gallerySlider = new GallerySlider(gallerySlides);
const testimonialCaretButtons = new CaretButtons(
  "testimonial__btn",
  "testimonial__heading"
);
const productsSlides = document.querySelectorAll(".product-card");
const productsSlider = new Slider(productsSlides);
productsSlider.removeFormat();
productsSlider.createSlides();

/**
 * If tablet query size matches creates the features and functions
 * for the components in tablet site.
 */
const tabletQueryFeatures = function () {
  if (tabletQuery.matches) {
    testimonialCaretButtons.createCaretBtns();
    featuredSlider.createSlides();
    gallerySlider.createSlides();
    observer.unobserve(sectionHeroEl);
  } else {
    observer.observe(sectionHeroEl);
    testimonialCaretButtons.removeCaretBtns();
    featuredSlider.removeSlides();
    gallerySlider.removeSlides();
  }
};

// Update when the window is resized
tabletQuery.addEventListener("change", tabletQueryFeatures);

// Initial check when browser is opened
tabletQueryFeatures();
