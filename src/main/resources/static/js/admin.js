const userMenuButton = document.querySelector("#user-menu");
const userMenuElement = document.querySelector(".nav__user-menu");

userMenuButton.addEventListener("click", function () {
    userMenuElement.classList.toggle("hidden");
});