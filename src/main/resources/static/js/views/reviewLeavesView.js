export const LEAF_SELECTED = "../img/icons/leaf-fill.svg";
export const LEAF_UNSELECTED = "../img/icons/leaf.svg";

/**
 * Returns the rounded rating from dataset of the given element.
 *
 * @param element the element to retrieve rating from.
 * @returns {number} the rounded rating.
 */
export function getRatingFromElement(element) {
    return Math.round(Number(element.dataset.rating));
}

/**
 * Makes the leaves which has a data rating attribute less than
 * or equal the given rating to selected.
 *
 * @param leaves the leaves to check and set as selected.
 * @param {*} rating the rating to make leaves selected for.
 */
export function setLeavesToSelected(leaves, rating) {
    leaves.forEach((leaf) => {
        if (getRatingFromElement(leaf) <= rating) {
            leaf.src = LEAF_SELECTED;
        } else {
            leaf.src = LEAF_UNSELECTED;
        }
    });
}

/**
 * Sets the rating leaves to filled on the various product(s) and reviews.
 */
export function setRatingLeavesOnProductsAndReviews() {
    const ratings = document.querySelectorAll(".product__rating");
    ratings.forEach((rating) => {
        const leaves = rating.querySelectorAll(".product__rating--img");
        setLeavesToSelected(leaves, getRatingFromElement(rating));
    });
}