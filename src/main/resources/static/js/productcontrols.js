/*
* Wishlist requests
*/

const addToFaveButtons = document.querySelectorAll("#add-to-fave");
const removeFromFaveButtons = document.querySelectorAll("#remove-from-fave");
const filledHeartIcon = `<i class="ph-heart-fill btn--icon btn--selected"></i>`;
const outlinedHeartIcon = `<i class="ph-heart-light btn--icon"></i>`;
const brokenHeartIcon = `<i class="ph-heart-break-light btn--icon"></i>`;

const getProductIdFromButton = function (button) {
    return button.dataset.id;
}

const addFilledHeartIcon = function (button) {
    button.innerHTML = "";
    button.insertAdjacentHTML("afterbegin", filledHeartIcon);
}

const addOutlinedHeartIcon = function (button) {
    button.innerHTML = "";
    button.insertAdjacentHTML("afterbegin", outlinedHeartIcon);
}

const addBrokenHeartIcon = function (button) {
    button.innerHTML = "";
    button.insertAdjacentHTML("afterbegin", brokenHeartIcon);
}

const changeHeartIcon = function (button) {
    if (button.innerHTML.valueOf().includes(outlinedHeartIcon.valueOf()) && window.location.pathname === "/wishlist") {
        addBrokenHeartIcon(button)
    } else if (button.innerHTML.valueOf().includes(outlinedHeartIcon.valueOf())) {
        addFilledHeartIcon(button);
    } else if (button.innerHTML.valueOf().includes(filledHeartIcon.valueOf())){
        addOutlinedHeartIcon(button);
    } else if (button.innerHTML.valueOf().includes(brokenHeartIcon.valueOf())){
        addOutlinedHeartIcon(button);
    }
}

const addToFavouritesRequest = function (event) {
    const addToFaveButton = event.target.closest("#add-to-fave");
    fetch(`${URL}/wishlist/${getProductIdFromButton(addToFaveButton)}`, {
        method: "POST",
    }).then(response => {
        if (response.ok) {
            changeHeartIcon(addToFaveButton);
        } else {
            userMenuButton.click();
        }
    });
};

const removeFromFavouritesRequest = function (event) {
    const removeFromFaveButton = event.target.closest("#remove-from-fave");
    fetch(`${URL}/wishlist/${getProductIdFromButton(removeFromFaveButton)}`, {
        method: "DELETE",
    }).then(response => {
        if (response.ok) {
            changeHeartIcon(removeFromFaveButton);
        }
    });
};

if (addToFaveButtons.length !== 0) {
    addToFaveButtons.forEach(addToFaveButton => addToFaveButton.addEventListener("click", addToFavouritesRequest));
}

if (removeFromFaveButtons.length !== 0) {
    removeFromFaveButtons.forEach(removeFromFaveButton => removeFromFaveButton.addEventListener("click", removeFromFavouritesRequest));
}