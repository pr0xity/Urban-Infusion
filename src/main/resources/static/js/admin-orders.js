const host = "http://localhost";
const port = ":8080";

function getOrders() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', host + port + "/orders/", true);
    req.onload  = function() {
        const jsonResponse = JSON.parse(req.responseText);
        loadOrders(jsonResponse)
    };
    req.send(null);
}

function loadOrders(orders) {
    for (let i = 0; i < orders.length; i++) {
        const order = orders[i];
        addOrderRow(order);
    }
}

function addOrderRow(order) {
    if (!document.getElementById("orderTable")) return;
    const tableBody = document.getElementById("orderTableBody");
    const row = document.createElement("tr");

    row.addEventListener("click", () => {
        window.location.href = "/orders/" + order["id"];
    })

    const dateCell = document.createElement("td");
    const orderIdCell = document.createElement("td");
    const customerIdCell = document.createElement("td");
    const statusCell = document.createElement("td");

    const dateNode = document.createTextNode(order["createdAt"]);
    const orderIdNode = document.createTextNode(order["id"]);
    const customerIdNode = document.createTextNode(order["user"]);
    let statusNode = document.createTextNode("Unprocessed");
    if (order["processed"] === "true") {
        statusNode = document.createTextNode("Completed");
    }

    dateCell.appendChild(dateNode);
    orderIdCell.appendChild(orderIdNode);
    customerIdCell.appendChild(customerIdNode);
    statusCell.appendChild(statusNode);

    row.appendChild(dateCell);
    row.appendChild(orderIdCell);
    row.appendChild(customerIdCell);
    row.appendChild(statusCell);
    tableBody.appendChild(row);
}