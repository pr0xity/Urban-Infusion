const LEAF_SELECTED = "../img/icons/leaf-fill.svg";
const LEAF_UNSELECTED = "../img/icons/leaf.svg";

/**
 * Makes the leaves below and equal the given rating
 * to selected.
 *
 * @param leaves
 * @param {*} rating the rating to make leaves selected for.
 */
function setLeavesToSelected(leaves, rating) {
  leaves.forEach((leaf) => {
    if (leaf.dataset.rating <= rating) {
      leaf.src = LEAF_SELECTED;
    } else {
      leaf.src = LEAF_UNSELECTED;
    }
  });
}

/**
 * Set the rating leaves on product and ratings.
 */
function setRatingLeaves() {
  const ratings = document.querySelectorAll(".product__rating");
  ratings.forEach(rating => {
    const leaves = rating.querySelectorAll(".product__rating--img");
    setLeavesToSelected(leaves, rating.dataset.rating);
  })
}

/**
 * Dynamically changes leaves on review-form.
 */
function reviewRating() {

  const ratingReview = document.querySelector("#review__rating")
  const ratingLeaves = ratingReview.querySelectorAll(".product__rating--img");
  let isRatingChosen = false;
  let rating;

  /**
   * Adds functionality for hover events when selecting review rating.
   *
   * @param {*} event hover event
   */
  function ratingsMouseOverHandler(event) {
    hoveredRating = event.target.dataset.rating;

    ratingLeaves.forEach((leaf) => {
      setLeavesToSelected(ratingLeaves, hoveredRating);
    });
  }

  /**
   * Adds functionality for when mouse hovering stops.
   */
  function ratingsMouseLeaveHandler() {
    ratingLeaves.forEach((leaf) => {
      if (isRatingChosen) {
        setLeavesToSelected(ratingLeaves, rating);
      } else {
        setAllLeavesToUnselected();
      }
    });
  }

  /**
   * Adds functionality for when rating is selected for review.
   *
   * @param {*} event click event for selecting rating.
   */
  function ratingsMouseClickHandler(event) {
    rating = event.target.dataset.rating;
    isRatingChosen = true;
  }

  /**
   * Makes all the leaves unselected.
   */
  function setAllLeavesToUnselected() {
    ratingLeaves.forEach((leaf) => (leaf.src = LEAF_UNSELECTED));
  }

  ratingLeaves.forEach((leaf) => {
    leaf.addEventListener("mouseover", ratingsMouseOverHandler);
    leaf.addEventListener("mouseleave", ratingsMouseLeaveHandler);
    leaf.addEventListener("click", ratingsMouseClickHandler);
  });
}

setRatingLeaves();

if (window.location.pathname.includes(PRODUCT_PATHNAME)) {
  reviewRating();
}