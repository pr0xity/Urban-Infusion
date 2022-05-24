function getTableContent() {
    getNewCustomers();
    getLatestOrders();
    getRecentRatings();
}

function getNewCustomers() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', URL + USERS_API_PATHNAME + "/new", true);
    req.onload  = function() {
        const jsonResponse = JSON.parse(req.responseText);
        loadCustomers(jsonResponse)
    };
    req.send(null);
}

function loadCustomers(users) {
    for (let i = 0; i < users.length; i++) {
        const user = users[i];
            addCustomerRow(user["id"], user["firstName"] + " " + user["lastName"], user["email"]);
    }
}

function addCustomerRow(id, name, email) {
    if (!document.getElementById("customerTable")) return;
    const tableBody = document.getElementById("customerTableBody");
    const row = document.createElement("tr");

    // row.addEventListener("click", () => {
    //     window.location.href = "/users/" + email;
    // })

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

function getLatestOrders() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', URL + ORDERS_API_PATHNAME + "/recent", true);
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

    // row.addEventListener("click", () => {
    //     window.location.href = ORDERS_API_PATHNAME + order["id"];
    // })

    const dateCell = document.createElement("td");
    const orderIdCell = document.createElement("td");
    const customerIdCell = document.createElement("td");
    const statusCell = document.createElement("td");

    let string = order["createdAt"];
    let trimmedString = string.substring(0,10);
    const dateNode = document.createTextNode(trimmedString);
    const orderIdNode = document.createTextNode(order["id"]);
    const customerIdNode = document.createTextNode(order["user"]["id"]);
    let statusNode = document.createTextNode("Unprocessed");
    if (order["processed"] === true) {
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