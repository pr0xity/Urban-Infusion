import {
  sendDeleteReviewRequest,
  sendGetAllReviewsRequest,
  sendUpdateReviewRequest,
} from "./controllers/reviewController.js";
import { hideElement } from "./tools.js";

const overlay = document.getElementById("overlay");
const editNameOverlay = document.getElementById("editNameOverlay");
const editNameButton = document.getElementById("editNameButton");

let ratings = null;
let rating = null;

/**
 * Initializes the page.
 */
const initializeRatings = function () {
  getRatings();
  setEventListeners();
};
document.addEventListener("DOMContentLoaded", initializeRatings);

/**
 * Fetches the reviews from the server and displays them in the ratings table.
 * @returns {Promise<void>}
 */
const getRatings = async function () {
  ratings = await sendGetAllReviewsRequest().finally();
  loadRatings(ratings);
};

/**
 * Filters the rating table to display only ratings that match the input in the search bar.
 */
const filterRatings = function () {
  const filteredRatings = [];
  const searchString = this.value.toLowerCase();
  for (let i = 0; i < ratings.length; i++) {
    const rating = ratings[i];
    let added = false;
    if (
      rating["user"]["id"].toString().includes(searchString) ||
      rating["displayName"].toString().toLowerCase().includes(searchString) ||
      rating["product"]["id"].toString().toLowerCase().includes(searchString) ||
      rating["product"]["name"].toString().toLowerCase().includes(searchString)
    ) {
      filteredRatings.push(rating);
      added = true;
    }
  }

  loadRatings(filteredRatings);
};

// Adds functionality to the search bar.
document.querySelector("#searchInput").addEventListener("input", filterRatings);

/**
 * Displays given ratings in the ratings table.
 *
 * @param ratings the ratings to be shown.
 */
function loadRatings(ratings) {
  document.getElementById("ratingTableBody").innerHTML = "";
  for (let i = 0; i < ratings.length; i++) {
    const rating = ratings[i];
    addRatingRow(rating);
  }
}

/**
 * Shows the given rating as a row in the ratings table.
 *
 * @param rating the rating to be shown.
 */
function addRatingRow(rating) {
  if (!document.getElementById("ratingTable")) return;
  const tableBody = document.getElementById("ratingTableBody");
  const row = document.createElement("tr");

  if (null != overlay) {
    row.addEventListener("click", () => {
      manageRating(rating);
      overlay.classList.toggle("hidden");
    });
  }

  const dateCell = document.createElement("td");
  const productIdCell = document.createElement("td");
  const userIdCell = document.createElement("td");
  const displayNameCell = document.createElement("td");
  const ratingCell = document.createElement("td");
  const commentCell = document.createElement("td");

  let string = rating["updatedAt"];
  let trimmedString = string.substring(0, 10);
  const dateNode = document.createTextNode(trimmedString);
  const productIdNode = document.createTextNode(rating["product"]["id"]);
  const userIdNode = document.createTextNode(rating["user"]["id"]);
  const displayNameNode = document.createTextNode(rating["displayName"]);
  const ratingNode = document.createTextNode(rating["rating"]);
  const commentNode = document.createTextNode(rating["comment"]);

  dateCell.appendChild(dateNode);
  productIdCell.appendChild(productIdNode);
  userIdCell.appendChild(userIdNode);
  displayNameCell.appendChild(displayNameNode);
  ratingCell.appendChild(ratingNode);
  commentCell.appendChild(commentNode);

  row.appendChild(dateCell);
  row.appendChild(productIdCell);
  row.appendChild(userIdCell);
  row.appendChild(displayNameCell);
  row.appendChild(ratingCell);
  row.appendChild(commentCell);
  tableBody.appendChild(row);
}

/**
 * Sets the information in the manage rating overlay and shows it to the user.
 *
 * @param managedRating the rating to be managed.
 */
const manageRating = function (managedRating) {
  rating = managedRating;
  const dateLabel = document.getElementById("dateLabel");
  const ratingIdLabel = document.getElementById("ratingIdLabel");
  const userIdLabel = document.getElementById("userIdLabel");
  const displayNameLabel = document.getElementById("displayNameLabel");
  const productIdLabel = document.getElementById("productIdLabel");
  const productNameLabel = document.getElementById("productNameLabel");
  const ratingLabel = document.getElementById("ratingLabel");
  const commentLabel = document.getElementById("commentLabel");

  dateLabel.textContent = rating["updatedAt"].substring(0, 10);
  ratingIdLabel.textContent = rating["id"];
  userIdLabel.textContent = rating["user"]["id"];
  displayNameLabel.textContent = rating["displayName"];
  productIdLabel.textContent = rating["product"]["id"];
  productNameLabel.textContent = rating["product"]["name"];
  ratingLabel.textContent = rating["rating"];
  commentLabel.textContent = rating["comment"];

  const deleteButton = document.getElementById("deleteReviewButton");
  deleteButton.onclick = function () {
    if (!overlay.classList.contains("hidden")) {
      deleteRating(rating);
    }
  };
};

/**
 * Sends a delete rating request to the server, and hides the manage rating overlay.
 *
 * @param rating the rating to be deleted.
 */
const deleteRating = function (rating) {
  sendDeleteReviewRequest(
    rating["product"]["id"],
    convertRatingToDTO(rating),
    getRatings
  ).finally(() => hideElement(overlay));
};

/**
 * Edits the display name of the rating being managed.
 */
const editName = function () {
  document
    .getElementById("updateNameButton")
    .addEventListener("click", updateRating);
  document.getElementById("newName").value = "";
  editNameOverlay.classList.add("display");
};

/**
 * Sends an update rating request to the server.
 */
const updateRating = function (event) {
  event.preventDefault();
  rating["displayName"] = document.getElementById("newName").value;
  sendUpdateReviewRequest(
    rating["product"]["id"],
    convertRatingToDTO(rating),
    updateRatingSuccess
  ).finally(() => hideElement(editNameOverlay));
};

/**
 * Upon successful update response from server,
 * update the information in the manage overlay,
 * and repopulate the rating table with the updated ratings.
 */
const updateRatingSuccess = function () {
  manageRating(rating);
  getRatings();
};

/**
 * Converts a rating to a ratingDTO object.
 * @param rating
 * @returns {{displayName: *, email: *, rating: *, comment: *}}
 */
const convertRatingToDTO = function (rating) {
  const dto = {};
  dto["displayName"] = rating["displayName"];
  dto["email"] = rating["user"]["email"];
  dto["rating"] = rating["rating"];
  dto["comment"] = rating["comment"];
  return dto;
};

function setEventListeners() {
  editNameButton.addEventListener("click", editName);
}
