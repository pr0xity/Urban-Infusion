const customerTable = document.getElementById("customerTable");
const token = document.getElementById("token");

function getCustomers() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', "http://localhost:8080/users", true);
    req.setRequestHeader("Authentication", token);
    req.onload  = function() {
        const jsonResponse = req.responseText;
        loadCustomers(jsonResponse)
    };
    req.send(null);
}

function loadCustomers(users) {
    for (let i = 0; i < users.length; i++) {
        const user = users[i];
        addRow(user[0], user[1] + " " + user[2], user[3]);
    }
}

function addRow(id, name, email) {
    if (!document.getElementById("customerTable")) return;
    const tableBody = document.getElementById("tbody").item(0);
    const row = document.createElement("tr");

    const idCell = document.createElement("td");
    const nameCell = document.createElement("td");
    const emailCell = document.createElement("td");

    const idNode = document.createTextNode(id);
    const nameNode = document.createTextNode(name);
    const emailNode = document.createElement(email);

    idCell.appendChild(idNode);
    nameCell.appendChild(nameNode);
    emailCell.appendChild(emailNode);

    row.appendChild(idCell);
    row.appendChild(nameCell);
    row.appendChild(emailCell);
    tableBody.appendChild(row);
}