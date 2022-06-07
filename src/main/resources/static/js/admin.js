const userMenuButton = document.querySelector("#user-menu");
const userMenuElement = document.querySelector(".nav__user-menu");
const searchInput = document.getElementById("searchInput");

userMenuButton.addEventListener("click", function () {
  userMenuElement.classList.toggle("hidden");
  window.addEventListener("mouseup", function (event) {
    if (!userMenuElement.contains(event.target)) {
      userMenuElement.classList.add("hidden");
    }
  });
});

const overlays = document.getElementsByClassName("overlay");
if (overlays.length > 0) {
  const closeButtons = document.getElementsByClassName("manage__btn--close");
  for (let i = 0; i < closeButtons.length; i++) {
    closeButtons[i].onclick = function () {
      hideOverlays();
    };
  }
  window.addEventListener("mouseup", function (event) {
    if (outsideOverlays(event)) {
      hideOverlays();
    }
  });
}

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

const hideOverlays = function () {
  for (let i = 0; i < overlays.length; i++) {
    overlays[i].classList.add("hidden");
  }
}

if (null != searchInput) {
  searchInput.onkeydown = function (event) {
    if (event.key === "Enter") {
      event.preventDefault();
      return false;
    }
  };
}
const closeButtons = document.getElementsByClassName("btn--close close");
if (closeButtons.length > 0) {
  for (let i = 0; i < closeButtons.length; i++) {
    const button = closeButtons[i];
    button.addEventListener("click", function () {
      hideEditOverlays();
    });
  }
}

const editOverlays = document.getElementsByClassName("edit__window");
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

export const hideEditOverlays = function () {
  const editOverlays = document.getElementsByClassName("edit__window");

  for (let i = 0; i < editOverlays.length; i++) {
    editOverlays[i].parentElement.classList.remove("display");
  }
};
