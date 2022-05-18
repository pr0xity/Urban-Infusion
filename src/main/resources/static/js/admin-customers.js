const customerTable = document.getElementById("userTable");
const tableBody = document.getElementById("userTableBody");
const overlay = document.getElementById("overlay");
const host = "http://localhost";
const port = ":8080";

function getUsers() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', host + port + "/users", true);
    req.onload  = function() {
        const jsonResponse = JSON.parse(req.responseText);
        loadUsers(jsonResponse)
    };
    req.send(null);
}

function loadUsers(users) {
    for (let i = 0; i < users.length; i++) {
        const user = users[i];
        addUserRow(user);
    }
}

function addUserRow(user) {
    if (!document.getElementById("userTable")) return;
    const row = document.createElement("tr");
    row.addEventListener("click", () => {
        manageUser(user);
        overlay.classList.toggle("show");
    })

    const idCell = document.createElement("td");
    const nameCell = document.createElement("td");
    const addressCell = document.createElement("td");

    const idNode = document.createTextNode(user["id"]);
    const nameNode = document.createTextNode(user["firstName"] + " " + user["lastName"]);
    let addressNode = document.createTextNode("");
    if (user["address"]) addressNode = document.createTextNode(user["address"]["addressLine"]);

    idCell.appendChild(idNode);
    nameCell.appendChild(nameNode);
    addressCell.appendChild(addressNode);

    row.appendChild(idCell);
    row.appendChild(nameCell);
    row.appendChild(addressCell);
    tableBody.appendChild(row);
}

function manageUser(user) {
    const idLabel = document.getElementById("customerIdLabel");
    const nameLabel = document.getElementById("customerNameLabel");
    const emailLabel = document.getElementById("customerEmailLabel");
    const passwordLabel = document.getElementById("customerPasswordLabel");
    const addressLabel = document.getElementById("customerAddressLabel");
    const createdAtLabel = document.getElementById("createdAtLabel");
    const lastLoginLabel = document.getElementById("lastLoginLabel");

    idLabel.textContent = user["id"];
    nameLabel.textContent = user["firstName"] + " " + user["lastName"];
    emailLabel.textContent = user["email"];
    passwordLabel.textContent = "*******";
    if (user["address"]) addressLabel.textContent = user["address"]["addressLine"];
    createdAtLabel.textContent = user["createdAt"].substring(0,10);
    lastLoginLabel.textContent = "yesterday";
}