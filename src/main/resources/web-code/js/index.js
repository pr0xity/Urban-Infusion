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
observer.observe(sectionHeroEl);

/**
 * //TODO: Move this to another file and import from it
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
  }

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
      this.slides[(+this.currentSlide + 2) % this.numberOfSlides].classList.add(
        "product-card__slide--secondary-prev"
      );
      this.slides[(+this.currentSlide + 1) % this.numberOfSlides].classList.add(
        "product-card__slide--secondary-next"
      );
    } else if (+this.numberOfSlides === 4) {
      this.slides[(+this.currentSlide + 1) % this.numberOfSlides].classList.add(
        "product-card__slide--secondary-next"
      );
      this.slides[(+this.currentSlide + 2) % this.numberOfSlides].classList.add(
        "hidden"
      );
      this.slides[(+this.currentSlide + 3) % this.numberOfSlides].classList.add(
        "product-card__slide--secondary-prev"
      );
    } else if (this.numberOfSlides > 4) {
      this.slides.forEach((slide) => slide.classList.add("hidden"));
      this.slides[this.currentSlide].classList.remove("hidden");
      this.slides[
        (+this.currentSlide + this.numberOfSlides - 1) % this.numberOfSlides
      ].classList.add("product-card__slide--secondary-prev");
      this.slides[
        (+this.currentSlide + this.numberOfSlides - 1) % this.numberOfSlides
      ].classList.remove("hidden");
      this.slides[
        (+this.currentSlide + this.numberOfSlides - 2) % this.numberOfSlides
      ].classList.add("product-card__slide--tertiary-prev");
      this.slides[
        (+this.currentSlide + this.numberOfSlides - 2) % this.numberOfSlides
      ].classList.remove("hidden");
      this.slides[(+this.currentSlide + 1) % this.numberOfSlides].classList.add(
        "product-card__slide--secondary-next"
      );
      this.slides[
        (+this.currentSlide + 1) % this.numberOfSlides
      ].classList.remove("hidden");
      this.slides[(+this.currentSlide + 2) % this.numberOfSlides].classList.add(
        "product-card__slide--tertiary-next"
      );
      this.slides[
        (+this.currentSlide + 2) % this.numberOfSlides
      ].classList.remove("hidden");
    }
    this.isFormatted = true;
  };

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
      slide.classList.remove("product-card__slide--active");
      slide.classList.remove("product-card__slide--secondary-next");
      slide.classList.remove("product-card__slide--secondary-prev");
      slide.classList.remove("product-card__slide--tertiary-next");
      slide.classList.remove("product-card__slide--tertiary-prev");
      slide.classList.remove("hidden");
    });
  };

  /**
   * Creates the caret buttons and dots for slider controls.
   */
  createSlideController = function () {
    // Insert controller in HTML
    let html = `<div class="slider-controls">
            <button aria-label="go to previous slide" class="slider__btn slider__btn--prev">
            <i class="ph-caret-left"></i>
            </button>
            <div class="dots">`;
    for (let i = 0; i < this.numberOfSlides; i++) {
      html += `<button aria-label="got to this slide" class="dots__dot" data-slide="${i}"></button>`;
    }

    html += `</div>
            <button aria-label="go to next slide"class="slider__btn slider__btn--next"><i class="ph-caret-right"></i></button>
            </div>`;
    this.slides[0]
      .closest(`:not(${this.slides[0].classList[0]})`)
      .insertAdjacentHTML("afterend", html);

    const sliderPrevBtn = this.element.querySelector(".slider__btn--prev");
    const sliderNextBtn = this.element.querySelector(".slider__btn--next");
    sliderPrevBtn.addEventListener("click", this.sliderPrev.bind(this));
    sliderNextBtn.addEventListener("click", this.sliderNext.bind(this));

    this.setActiveDot();
    this.dots.forEach((dot) => {
      dot.addEventListener("click", (event) => this.goToSlide(event));
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
    // event.preventDefault();
    let slide;
    if (this.numberOfSlides > 4) {
      slide = document.querySelector(".product-card__slide--tertiary-prev");
      slide.style.opacity = "0";
      slide.style.transform = "translateX(-300%)";
      setTimeout(function () {
        slide.style.display = "none";
      }, 50);
    }
    if (+this.currentSlide === this.numberOfSlides - 1) {
      this.currentSlide = 0;
    } else {
      Number((this.currentSlide += 1));
    }

    this.goToSlide();

    if (this.numberOfSlides > 4) {
      setTimeout(function () {
        slide.style.transform = "translateX(300%)";
        slide.style.opacity = "0";
        slide.style.display = "initial";
      }, 100);
      setTimeout(function () {
        slide.style.transform = "translateX(170%)";
        slide.style.opacity = "1";
      }, 110);
      setTimeout(function () {
        slide.setAttribute("style", "");
      }, 200);
    }
  };

  /**
   * Goes to the previous slide.
   *
   * @param {*} event event used for this handler
   */
  sliderPrev = function (event) {
    // event.preventDefault();
    let slide;
    if (this.numberOfSlides > 4) {
      slide = document.querySelector(".product-card__slide--tertiary-next");
      // slide.classList.add("hidden");
      slide.style.opacity = "0";
      slide.style.transform = "translateX(500%)";
      setTimeout(function () {
        slide.style.display = "none";
      }, 50);
    }
    if (+this.currentSlide === 0) {
      this.currentSlide = this.numberOfSlides - 1;
    } else {
      this.currentSlide -= 1;
    }

    // this.setActiveDot();
    this.goToSlide();
    if (this.numberOfSlides > 4) {
      setTimeout(function () {
        slide.style.transform = "translateX(-500%)";
        slide.style.opacity = "0";
        slide.style.display = "initial";
      }, 100);
      setTimeout(function () {
        slide.style.transform = "translateX(-270%)";
        slide.style.opacity = "1";
      }, 110);
      setTimeout(function () {
        slide.setAttribute("style", "");
      }, 200);
    }
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
      if (event.target.classList[0].includes(this.slides[0].classList[0])) {
        this.currentSlide = Number(
          event.target.closest(`.${this.slides[0].classList[0]}`).dataset.slide
        );
      } else if (event.target.classList[0].includes("dots__dot")) {
        console.log("here?");
        this.currentSlide = Number(event.target.dataset.slide);
      }
    }

    if (this.isFormatted) {
      this.removeFormat();
      this.formatSlides();
    } else {
      this.slides[(this.currentSlide + 1) % this.numberOfSlides].classList.add(
        "hidden"
      );
      this.slides[this.currentSlide].classList.remove("hidden");
      this.slides[(this.currentSlide + 2) % this.numberOfSlides].classList.add(
        "hidden"
      );
    }
    this.setActiveDot();
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
  } else {
    testimonialCaretButtons.removeCaretBtns();
    featuredSlider.removeSlides();
    gallerySlider.removeSlides();
  }
};

// Update when the window is resized
tabletQuery.addEventListener("change", tabletQueryFeatures);

// Initial check when browser is opened
tabletQueryFeatures();
