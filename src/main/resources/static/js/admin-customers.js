const customerTable = document.getElementById("customerTable");
const host = "http://localhost";
const port = ":8080";

function getCustomers() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', host + port + "/users", true);
    req.onload  = function() {
        const jsonResponse = JSON.parse(req.responseText);
        loadCustomers(jsonResponse)
    };
    req.send(null);
}

function loadCustomers(users) {
    for (let i = 0; i < users.length; i++) {
        const user = users[i];
        if (user["address"]) {
            addRow(user["id"], user["firstName"] + " " + user["lastName"], user["address"].addressLine);
        } else {
            addRow(user["id"], user["firstName"] + " " + user["lastName"], "");
        }
    }
}

function addRow(id, name, email) {
    if (!document.getElementById("customerTable")) return;
    const tableBody = document.getElementById("tbody");
    const row = document.createElement("tr");

    const idCell = document.createElement("td");
    const nameCell = document.createElement("td");
    const emailCell = document.createElement("td");

    const idNode = document.createTextNode(id);
    const nameNode = document.createTextNode(name);
    const emailNode = document.createTextNode(email);

    idCell.appendChild(idNode);
    nameCell.appendChild(nameNode);
    emailCell.appendChild(emailNode);

    row.appendChild(idCell);
    row.appendChild(nameCell);
    row.appendChild(emailCell);
    tableBody.appendChild(row);
}