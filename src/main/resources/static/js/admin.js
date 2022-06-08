const userMenuButton = document.querySelector("#user-menu");
const userMenuElement = document.querySelector(".nav__user-menu");
const searchInput = document.getElementById("searchInput");
const manageOverlays = document.getElementsByClassName("overlay");
const editOverlays = document.getElementsByClassName("edit__window");

userMenuButton.addEventListener("click", function () {
  userMenuElement.classList.toggle("hidden");
  window.addEventListener("mouseup", function (event) {
    if (!userMenuElement.contains(event.target)) {
      userMenuElement.classList.add("hidden");
    }
  });
});

// Adds functionality for hiding modal windows.
if (manageOverlays.length > 0) {
  // Adds click event to close buttons on manage overlays, if any manage overlays exist in current dom.
  const closeButtons = document.getElementsByClassName("manage__btn--close");
  for (let i = 0; i < closeButtons.length; i++) {
    closeButtons[i].onclick = function () {
      hideManageOverlays();
    };
  }
  // Hides all manage overlays if mouse is clicked outside a manage overlay.
  window.addEventListener("mouseup", function (event) {
    if (outsideOverlays(event)) {
      hideManageOverlays();
    }
  });
}

/**
 * Checks whether given event targets an element outside a modal window or not.
 *
 * @param event the event to check the target of.
 * @returns {boolean} true if target was outside any overlays.
 */
const outsideOverlays = function (event) {
  const windows = document.getElementsByClassName("modal__window");
  let outside = true;
  for (let i = 0; i < windows.length; i++) {
    if (windows[i].contains(event.target)) {
      outside = false;
    }
  }
  return outside;
}

/**
 * Hides all manage overlays.
 */
const hideManageOverlays = function () {
  for (let i = 0; i < manageOverlays.length; i++) {
    manageOverlays[i].classList.add("hidden");
  }
}

/**
 * Disables page reload if enter is clicked in a search bar.
 */
if (null != searchInput) {
  searchInput.onkeydown = function (event) {
    if (event.key === "Enter") {
      event.preventDefault();
      return false;
    }
  };
}

/**
 * Adds click event to close buttons on edit modal windows.
 */
const closeButtons = document.getElementsByClassName("btn--close close");
if (closeButtons.length > 0) {
  for (let i = 0; i < closeButtons.length; i++) {
    const button = closeButtons[i];
    button.addEventListener("click", function () {
      hideEditOverlays();
    });
  }
}

/**
 * Hides all edit overlays if mouse is clicked outside an edit overlay.
 */
if (editOverlays.length > 0) {
  window.addEventListener("mouseup", function (event) {
    let inWindow = false;
    for (let i = 0; i < editOverlays.length; i++) {
      const editOverlay = editOverlays[i];
      if (editOverlay.contains(event.target)) {
        inWindow = true;
      }
    }
    if (!inWindow) {
      hideEditOverlays();
    }
  });
}

/**
 * Hides all edit overlays.
 */
export const hideEditOverlays = function () {
  for (let i = 0; i < editOverlays.length; i++) {
    editOverlays[i].parentElement.classList.remove("display");
  }
};
