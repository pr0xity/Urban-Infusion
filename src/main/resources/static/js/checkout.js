"use strict";

const open_modal = document.querySelector(".open_modal");
const checkout_container = document.querySelector(".checkout__complete");
const checkout_overlay = document.querySelector(".checkout__complete--overlay");
const close_modal = document.querySelector(".checkout__btn--close");
const address = document.querySelector(".checkout__info--address").innerText;

addListeners();

function addListeners() {
  open_modal.addEventListener("click", () => {
    checkout_container.classList.add("show");
    checkout_overlay.classList.add("show");
  });
  close_modal.addEventListener("click", () => {
    checkout_container.classList.remove("show");
    checkout_overlay.classList.remove("show");
  });
}

//Render map
getAddressInfo(address).then(data => renderMap(data[0], data[1]));