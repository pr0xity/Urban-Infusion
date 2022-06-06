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

if (document.getElementsByClassName("overlay").length > 0) {
  const overlay = document.getElementById("overlay");
  const closeButton = document.getElementById("closeButton");
  closeButton.onclick = function () {
    overlay.classList.add("hidden");
  };
  window.addEventListener("mouseup", function (event) {
    if (!document.getElementById("overlayWindow").contains(event.target)) {
      overlay.classList.add("hidden");
    }
  });
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
