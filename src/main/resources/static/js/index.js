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
 * Checks screen size and changes layout accordingly for index page.
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
