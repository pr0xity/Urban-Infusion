const overlay = document.getElementById("overlay");
const editOverlay = document.getElementsByClassName("edit__window")[0];

let ratings = null;

const getRatings = function() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', URL + RATING_API_PATHNAME, true);
    req.onload  = function() {
       ratings = JSON.parse(req.responseText);
        loadRatings(ratings)
    };
    req.send(null);
}

const getRecentRatings = function() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', URL + RATING_API_PATHNAME + "/recent", true);
    req.onload  = function() {
        ratings = JSON.parse(req.responseText);
        loadRatings(ratings)
    };
    req.send(null);}

const filterRatings = function() {
    const filteredRatings = [];
    const searchString = searchInput.value.toLowerCase();
    for (let i = 0; i < ratings.length; i++) {
        const rating = ratings[i];
        let added = false;
        if (rating["user"]["id"].toString().includes(searchString) ||
            rating["displayName"].toString().toLowerCase().includes(searchString) ||
            rating["product"]["id"].toString().toLowerCase().includes(searchString) ||
            rating["product"]["name"].toString().toLowerCase().includes(searchString)) {
            filteredRatings.push(rating);
            added = true;
        }
    }

    loadRatings(filteredRatings);
}

const loadRatings = function(ratings) {
    document.getElementById("ratingTableBody").innerHTML = "";
    for (let i = 0; i < ratings.length; i++) {
        const rating = ratings[i];
        addRatingRow(rating);
    }
}

const addRatingRow = function(rating) {
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
    let trimmedString = string.substring(0,10);
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

const manageRating = function(rating) {
    const dateLabel = document.getElementById("dateLabel");
    const ratingIdLabel = document.getElementById("ratingIdLabel");
    const userIdLabel = document.getElementById("userIdLabel");
    const displayNameLabel = document.getElementById("displayNameLabel");
    const productIdLabel = document.getElementById("productIdLabel");
    const productNameLabel = document.getElementById("productNameLabel");
    const ratingLabel = document.getElementById("ratingLabel");
    const commentLabel = document.getElementById("commentLabel");

    dateLabel.textContent = rating["updatedAt"].substring(0,10);
    ratingIdLabel.textContent = rating["id"];
    userIdLabel.textContent = rating["user"]["id"];
    displayNameLabel.textContent = rating["displayName"];
    productIdLabel.textContent = rating["product"]["id"];
    productNameLabel.textContent = rating["product"]["name"];
    ratingLabel.textContent = rating["rating"];
    commentLabel.textContent = rating["comment"];

    const deleteButton = document.getElementById("deleteReviewButton");
    deleteButton.onclick = function() {
        if (!overlay.classList.contains("hidden")) {
            deleteRating(rating);
        }
    };
}

const deleteRating = function(rating) {
    sendApiRequest(
        RATING_API_PATHNAME + "/" + rating["product"]["id"],
        "DELETE",
        convertRatingToDTO(rating),
        getRatings,
        null);
    overlay.classList.toggle("hidden");
}

const updateRating = function(rating) {
    sendApiRequest(RATING_API_PATHNAME + "/" + rating["id"],
        "PUT",
        convertRatingToDTO(rating),
        getRatings,
        null);
    editOverlay.classList.toggle("display");
}

const convertRatingToDTO = function(rating) {
    const dto = {};
    dto["displayName"] = rating["displayName"];
    dto["email"] = rating["user"]["email"];
    dto["rating"] = rating["rating"];
    dto["comment"] = rating["comment"];
    return dto;
}