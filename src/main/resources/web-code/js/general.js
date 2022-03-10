const userMenuBtn = document.querySelector("#user-menu");
const userMenuEl = document.querySelector(".nav__user-menu");

userMenuBtn.addEventListener("click", function (event) {
  userMenuEl.classList.toggle("hidden");
  console.log(event.target);
});
