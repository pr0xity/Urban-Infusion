const host = "http://localhost";
const port = ":8080";
let orders = null;
const searchInput = document.getElementById("searchInput");
const tableBody = document.getElementById("orderTableBody");
const manageOrderTableBody = document.getElementById("manageOrderTableBody");
const overlay = document.getElementById("overlay");
const processedCheckBox = document.getElementById("orderProcessed");

function initializeOrders() {
    getOrders();
    searchInput.onkeydown = function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            return false;
        }
    }
}

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
    if (!document.getElementById("orderTable")) return;
    const row = document.createElement("tr");
    row.addEventListener("click", () => {
        manageOrder(order);
        overlay.classList.toggle("hidden");
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

    orderNameLabel.textContent = order["user"]["firstName"] + " " + order["user"]["lastName"];
    orderEmailLabel.textContent = order["user"]["email"];
    let address = order["user"]["address"]["addressLine"];
    orderAddressLabel.innerHTML = address.replaceAll(",", ",&ZeroWidthSpace;");
    let string = order["createdAt"];
    orderDateLabel.textContent = string.substring(0, 10);
    orderIdLabel.textContent = order["id"];

    processedCheckBox.onclick = function() {
        if (!overlay.classList.contains("hidden")) {
            updateOrder(order);
        }
    };

    loadOrderItems(order);

    processedCheckBox.checked = order["processed"] === true;
}

function updateOrder(order) {
    let dto = {};
    let itemIds = [];
    let quantities = [];
    for (let i = 0; i < order["orderItems"].length; i++) {
        itemIds.push(order["orderItems"][i]["product"]["id"]);
        quantities.push(order["orderItems"][i]["quantity"]);
    }
    dto["id"] = order["id"];
    dto["itemIds"] = itemIds;
    dto["quantities"] = quantities;
    dto["processed"] = processedCheckBox.checked;

    const req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if (req.readyState === 4) {
            getOrders();
        }
    };
    req.overrideMimeType("application/json");
    req.open('PUT', host + port + ORDERS_API_PATHNAME + "/" + order["id"], true);
    req.send(JSON.stringify(dto));
}


function loadOrderItems(order) {
    const items = order["orderItems"];
    manageOrderTableBody.innerHTML="";
    for (let i = 0; i < items.length; i++) {
        const item = items[i];
        addOrderItemRow(item);
    }
}

function addOrderItemRow(item) {
    if (!document.getElementById("manageOrderTable")) return;
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
    manageOrderTableBody.appendChild(row);
}