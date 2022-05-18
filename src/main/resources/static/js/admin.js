const userMenuButton = document.querySelector("#user-menu");
const userMenuElement = document.querySelector(".nav__user-menu");

userMenuButton.addEventListener("click", function () {
    userMenuElement.classList.toggle("hidden");
});

if (document.getElementsByClassName("overlay")) {
    const overlay = document.getElementById("overlay");
    const closeButton = document.getElementById("closeButton")
    closeButton.addEventListener("click", () => {
        overlay.classList.remove("show");
    });
    window.addEventListener('mouseup', function(event){
        if (!document.getElementById("overlayWindow").contains(event.target)){
            overlay.classList.remove("show");
        }
    });
}