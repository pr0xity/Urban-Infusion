const host = "http://localhost";
const port = ":8080";
let orders = null;
const searchInput = document.getElementById("searchInput");
const tableBody = document.getElementById("orderTableBody");

function getOrders() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', host + port + "/orders/", true);
    req.onload  = function() {
        orders = JSON.parse(req.responseText);
        loadOrders(orders)
    };
    req.send(null);
}

function filterOrders() {
    const filteredOrders = [];
    const searchString = searchInput.value.toLowerCase();
    for (let i = 0; i < orders.length; i++) {
        const order = orders[i];
        let added = false;
        if (order["id"].toString().includes(searchString) || order["user"]["id"].toString().includes(searchString)) {
            filteredOrders.push(order);
            added = true;
        }
        for (let j = 0; j < order["orderItems"].length; j++) {
            const item = order["orderItems"][j];
            if (item["product"]["name"].toLowerCase().includes(searchString) && !added) {
                filteredOrders.push(order);
                break;
            }
        }
    }
    loadOrders(filteredOrders);
}

function loadOrders(orders) {
    tableBody.innerHTML = "";
    for (let i = 0; i < orders.length; i++) {
        const order = orders[i];
        addOrderRow(order);
    }
}

function addOrderRow(order) {
    const row = document.createElement("tr");

    row.addEventListener("click", () => {
        manageOrder(order);
    })

    const dateCell = document.createElement("td");
    const orderIdCell = document.createElement("td");
    const customerIdCell = document.createElement("td");
    const itemsCell = document.createElement("td");
    const statusCell = document.createElement("td");

    let string = order["createdAt"];
    let trimmedString = string.substring(0,10);
    const dateNode = document.createTextNode(trimmedString);
    const orderIdNode = document.createTextNode(order["id"]);
    const customerIdNode = document.createTextNode(order["user"]["id"]);

    let items = "";
    for (let i = 0; i < Math.min(order["orderItems"].length, 2); i++) {
        let item = order["orderItems"][i];
        if (i > 0) {
            items += ", ";
        }
        items += item["quantity"] + " x " + item["product"]["name"];
    }
    if (order["orderItems"].length > 2) {
        items += "...";
    }
    const itemsNode = document.createTextNode(items)
    let statusNode = document.createTextNode("Unprocessed");
    if (order["processed"] === true) {
        statusNode = document.createTextNode("Completed");
    }

    dateCell.appendChild(dateNode);
    orderIdCell.appendChild(orderIdNode);
    customerIdCell.appendChild(customerIdNode);
    itemsCell.appendChild(itemsNode);
    statusCell.appendChild(statusNode);

    row.appendChild(dateCell);
    row.appendChild(orderIdCell);
    row.appendChild(customerIdCell);
    row.appendChild(itemsCell);
    row.appendChild(statusCell);
    tableBody.appendChild(row);
}

function manageOrder(order) {
    const orderNameLabel = document.getElementById("orderNameLabel");
    const orderEmailLabel = document.getElementById("orderEmailLabel");
    const orderAddressLabel = document.getElementById("orderAddressLabel");
    const orderDateLabel = document.getElementById("orderDateLabel");
    const orderIdLabel = document.getElementById("orderIdLabel");
    const processedCheckBox = document.getElementById("orderProcessed");

    orderNameLabel.textContent = order["user"]["firstName"] + " " + order["user"]["lastName"];
    orderEmailLabel.textContent = order["user"]["email"];
    orderAddressLabel.textContent = order["user"]["address"]["addressLine"];
    orderDateLabel.textContent = order["date"];
    orderIdLabel.textContent = order["id"];

    loadOrderItems(order);

    if (order["processed"] === true) {
        processedCheckBox.checked = true;
    }
}

function loadOrderItems(order) {
    const items = order["orderItems"];
    for (let i = 0; i < items.length; i++) {
        const item = items[i];
        addOrderItemRow(item);
    }
}

function addOrderItemRow(item) {
    if (!document.getElementById("manageOrderTable")) return;
    const tableBody = document.getElementById("manageOrderTableBody");
    const row = document.createElement("tr");

    const productNameCell = document.createElement("td");
    const productIdCell = document.createElement("td");
    const quantityCell = document.createElement("td");

    const productNameNode = document.createTextNode(item["product"]["name"]);
    const productIdNode = document.createTextNode(item["product"]["id"]);
    const quantityNode = document.createTextNode(item["quantity"]);

    productNameCell.appendChild(productNameNode);
    productIdCell.appendChild(productIdNode);
    quantityCell.appendChild(quantityNode);

    row.appendChild(productNameCell);
    row.appendChild(productIdCell);
    row.appendChild(quantityCell);
    tableBody.appendChild(row);
}