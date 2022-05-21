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
  if (mobileLayoutSize.matches) {
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
mobileLayoutSize.addEventListener("change", tabletQueryFeatures);

// Initial check when browser is opened
tabletQueryFeatures();
