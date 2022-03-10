const headerEl = document.querySelector(".header");
const sectionHeroEl = document.querySelector(".section-hero");
const sectionCooperationsEl = document.querySelector(".section-cooperations");
const tabletQuery = window.matchMedia("(max-width: 54em)");
const footerHeadingsEl = document.querySelectorAll(".footer-heading");
const navMobileBtns = document.querySelectorAll(".nav-mobile__btn");

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
 * Represents a slider
 */
class Slider {
  sliderInstance = this;
  slides;
  elemnt;
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
    this.slides.forEach((slide, index) => {
      slide.addEventListener("click", (event) => {
        this.currentSlide = index;
        this.goToSlide();
      });
    });
  }

  /**
   * Formats the slides
   */
  formatSlides = function () {
    console.log(this.currentSlide);
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
    sliderPrevBtn.addEventListener("click", (event) => this.sliderPrev(event));
    sliderNextBtn.addEventListener("click", (event) => this.sliderNext(event));

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
    console.log(slideController);
    slideController.forEach((controller) => {
      if (this.element.contains(controller)) {
        console.log(controller.parentNode);
        // this.element.removeChild(controller);
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
    event.preventDefault();
    let slide;
    if (this.numberOfSlides > 4) {
      slide = document.querySelector(".product-card__slide--tertiary-prev");
      console.log(slide);
      // slide.classList.add("hidden");
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

    // this.setActiveDot();
    this.goToSlide(this.currentSlide);
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
    event.preventDefault();
    let slide;
    if (this.numberOfSlides > 4) {
      slide = document.querySelector(".product-card__slide--tertiary-next");
      console.log(slide);
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
    this.goToSlide(this.currentSlide);
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
    // If event is a pointerevent (has pointer id 1)
    console.log(event);
    if (event != undefined && event.pointerId === 1) {
      this.currentSlide = Number(event.target.dataset.slide);
      console.log(this.currentSlide);
    }
    if (this.isFormatted) {
      this.removeFormat();
      this.formatSlides();
    } else {
      this.removeFormat();
      this.slides[(+this.currentSlide + 1) % this.numberOfSlides].classList.add(
        "hidden"
      );
      this.slides[(+this.currentSlide + 2) % this.numberOfSlides].classList.add(
        "hidden"
      );
    }
    this.setActiveDot();
  };
}

class GallerySlider extends Slider {
  constructor(slides) {
    super(slides);
  }

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
  companyEl = document.querySelector(".company-grid");

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
    console.log(slideController);
    slideController.forEach((controller) => {
      if (this.companyEl.contains(controller)) {
        this.companyEl.removeChild(controller);
      }
    });
  };
}

/**
 * Creates caret buttons for the given list of elements.
 *
 * @param {*} elements The list of elements to create caret buttons for.
 */
const createCaretBtns = function (elements, btnClass) {
  let i = 0;
  elements.forEach((element) => {
    element.insertAdjacentHTML(
      "beforeend",
      `<button class="${btnClass}" data-item="${i}"><i class="ph-caret-left"></i></button>`
    );
    i++;
  });
};

/**
 * Removes the caret buttons from the given list of elements.
 *
 * @param {*} elements The list of elements to remove caret button from.
 */
const removeCaretBtns = function (elements) {
  elements.forEach((element) => {
    if (element.childNodes[1] !== undefined) {
      element.removeChild(element.childNodes[1]);
    }
  });
};

/**
 * Iterates over footer buttons and adds listeners to the buttons.
 * Opens the footer list for the corresponding button.
 */
const testimonialsButtons = function () {
  const footerBtns = document.querySelectorAll(".testimonial__btn");

  const footerListsEl = document.body.querySelectorAll(".testimonial__text");

  footerBtns.forEach((button) => {
    // Initial degrees value
    let degrees = 0;

    // Initial display value
    let display = "none";

    button.addEventListener("click", function (event) {
      const clicked = event.target.closest(".testimonial__btn");

      let footerListIndex = clicked.dataset.item;

      if (degrees === 0) {
        degrees = -90;
        display = "initial";
      } else {
        degrees = 0;
        display = "none";
      }

      clicked.style.transform = `rotate( ${degrees}deg)`;

      footerListsEl[footerListIndex].style.display = `${display}`;
    });
  });
};

/**
 * Iterates over footer buttons and adds listeners to the buttons.
 * Opens the footer list for the corresponding button.
 */
const footerButtons = function () {
  const footerBtns = document.querySelectorAll(".footer__btn");

  const footerListsEl = document.body.querySelectorAll(".footer__list");

  footerBtns.forEach((button) => {
    // Initial degrees value
    let degrees = 0;

    // Initial display value
    let display = "none";

    button.addEventListener("click", function (event) {
      const clicked = event.target.closest(".footer__btn");

      let footerListIndex = clicked.dataset.item;

      if (degrees === 0) {
        degrees = -90;
        display = "initial";
      } else {
        degrees = 0;
        display = "none";
      }

      clicked.style.transform = `rotate( ${degrees}deg)`;

      footerListsEl[footerListIndex].style.display = `${display}`;
    });
  });
};

/**
 * If tablet query size matches creates the features and functions
 * for the components in tablet site.
 */
const tabletQueryFeatures = function () {
  const featuredSlides = document.querySelectorAll(".featured-card");
  const gallerySlides = document.querySelectorAll(".gallery");
  const gallerySlider = new GallerySlider(gallerySlides);
  const featuredSlider = new Slider(featuredSlides);
  const navListEl = document.querySelector(".nav__list");
  const testimonialHeadingsEl = document.querySelectorAll(
    ".testimonial__heading"
  );

  if (tabletQuery.matches) {
    document.body.classList.add("sticky");
    featuredSlider.formatSlides();
    featuredSlider.createSlideController();
    gallerySlider.createSlides();

    createCaretBtns(footerHeadingsEl, "footer__btn");
    footerButtons();
    createCaretBtns(testimonialHeadingsEl, "testimonial__btn");
    testimonialsButtons();
    navListEl.classList.add("hidden");
    navMobileBtns.forEach((btn) =>
      btn.addEventListener("click", (event) => {
        navMobileBtns[0].classList.toggle("hidden");
        navMobileBtns[1].classList.toggle("hidden");
        document.body.querySelector(".overlay").classList.toggle("hidden");
        navListEl.classList.toggle("hidden");
      })
    );
  } else {
    navListEl.classList.remove("hidden");
    observer.observe(sectionHeroEl);
    featuredSlider.removeFormat();
    featuredSlider.removeSlideController();
    gallerySlider.removeSlides();
    removeCaretBtns(footerHeadingsEl);
    removeCaretBtns(testimonialHeadingsEl);
  }
};

const productsSlides = document.querySelectorAll(".product-card");
const productsSlider = new Slider(productsSlides);
productsSlider.removeFormat();
productsSlider.formatSlides();
productsSlider.createSlideController();

// Update when the window is resized
tabletQuery.addEventListener("change", tabletQueryFeatures);

// Initial check when browser is opened
tabletQueryFeatures();
