function reviewRating() {
  const ratingLeaves = document.querySelectorAll(".product__rating--img");
  let isRatingChosen = false;
  let rating;

  const LEAF_SELECTED = "../img/icons/leaf-fill.svg";
  const LEAF_UNSELECTED = "../img/icons/leaf.svg";

  /**
   * Adds functionality for hover events when selecting review rating.
   *
   * @param {*} event hover event
   */
  function ratingsMouseOverHandler(event) {
    hoveredRating = event.target.dataset.rating;

    ratingLeaves.forEach((leaf) => {
      setLeavesToSelected(hoveredRating);
    });
  }

  /**
   * Adds functionality for when mouse hovering stops.
   */
  function ratingsMouseLeaveHandler() {
    ratingLeaves.forEach((leaf) => {
      if (isRatingChosen) {
        setLeavesToSelected(rating);
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
   * Makes the leaves below and equal the given rating
   * to selected.
   *
   * @param {*} rating the rating to make leaves selected for.
   */
  function setLeavesToSelected(rating) {
    ratingLeaves.forEach((leaf) => {
      if (leaf.dataset.rating <= rating) {
        leaf.src = LEAF_SELECTED;
      } else {
        leaf.src = LEAF_UNSELECTED;
      }
    });
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

reviewRating();
