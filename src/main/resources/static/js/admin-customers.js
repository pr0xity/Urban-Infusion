const customerTable = document.getElementById("userTable");
const tableBody = document.getElementById("userTableBody");
const overlay = document.getElementById("overlay");
const purchaseHistoryTable = document.getElementById("purchaseHistoryTable");
const purchaseHistoryTableBody = document.getElementById("purchaseHistoryTableBody");

const editFullNameButton = document.getElementById("editFullNameButton");
const editEmailButton = document.getElementById("editEmailButton");
const editAddressButton = document.getElementById("editAddressButton");
const editFullNameOverlay = document.getElementById("editFullNameOverlay");
const editEmailOverlay = document.getElementById("editEmailOverlay");
const editAddressOverlay = document.getElementById("editAddressOverlay");

let users = null;
let user = null;

const initialCustomers = function() {
    getUsers();
    setEventListeners();
}

const getUsers = function() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', URL + USERS_API_PATHNAME, true);
    req.onload  = function() {
        users = JSON.parse(req.responseText);
        loadUsers(users)
    };
    req.send(null);
}

const loadUsers = function(users) {
    tableBody.innerHTML = "";
    for (let i = 0; i < users.length; i++) {
        const user = users[i];
        addUserRow(user);
    }
}

const addUserRow = function(user) {
    if (!document.getElementById("userTable")) return;
    const row = document.createElement("tr");
    row.addEventListener("click", () => {
        manageUser(user);
        overlay.classList.toggle("hidden");
    })

    const idCell = document.createElement("td");
    const nameCell = document.createElement("td");
    const emailCell = document.createElement("td");
    const addressCell = document.createElement("td");

    const idNode = document.createTextNode(user["id"]);
    const nameNode = document.createTextNode(user["firstName"] + " " + user["lastName"]);
    const emailNode = document.createTextNode(user["email"]);
    let addressNode = document.createTextNode("");
    if (user["address"]) addressNode = document.createTextNode(user["address"]["addressLine"]);

    idCell.appendChild(idNode);
    nameCell.appendChild(nameNode);
    emailCell.appendChild(emailNode);
    addressCell.appendChild(addressNode);

    row.appendChild(idCell);
    row.appendChild(nameCell);
    row.appendChild(emailCell);
    row.appendChild(addressCell);
    tableBody.appendChild(row);
}

const manageUser = function(managedUser) {
    user = managedUser;
    const idLabel = document.getElementById("customerIdLabel");
    const nameLabel = document.getElementById("customerNameLabel");
    const emailLabel = document.getElementById("customerEmailLabel");
    const passwordLabel = document.getElementById("customerPasswordLabel");
    const addressLabel = document.getElementById("customerAddressLabel");
    const createdAtLabel = document.getElementById("createdAtLabel");
    const lastLoginLabel = document.getElementById("lastLoginLabel");

    idLabel.textContent = managedUser["id"];
    nameLabel.textContent = managedUser["firstName"] + " " + managedUser["lastName"];
    emailLabel.textContent = managedUser["email"];
    passwordLabel.textContent = "*******";
    if (managedUser["address"]) addressLabel.textContent = managedUser["address"]["addressLine"];
    createdAtLabel.textContent = managedUser["createdAt"].substring(0,10);
    // todo: delete or implement last login.
    lastLoginLabel.textContent = "yesterday";

    fetchOrders(managedUser);
}

const fetchOrders = function(user) {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', URL + ORDERS_API_PATHNAME, true);
    req.onload  = function() {
        const allOrders = JSON.parse(req.responseText);
        populatePurchaseHistory(user, allOrders);
    };
    req.send(null);
}

const populatePurchaseHistory = function(user, allOrders) {
    purchaseHistoryTableBody.innerHTML = "";
    const orders = [];
    for (let i = 0; i < allOrders.length; i++) {
        if (allOrders[i]["user"]["id"] === user["id"]) {
            orders.push(allOrders[i]);
        }
    }

    for (let i = 0; i < orders.length; i++) {
        addPurchaseHistoryRow(orders[i]);
    }
}

const addPurchaseHistoryRow = function(order) {
    const row = document.createElement("tr");
    const dateCell = document.createElement("td");
    const idCell = document.createElement("td");
    const itemCell = document.createElement("td");
    const totalCell = document.createElement("td");
    const statusCell = document.createElement("td");

    const dateNode = document.createTextNode(order["createdAt"].substring(0,10));
    const idNode = document.createTextNode(order["id"]);


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

    const totalNode = document.createTextNode(order["total"]);
    let statusNode = document.createTextNode("Pending");
    if (order["processed"] === true) {
        statusNode = document.createTextNode("Completed");
    }

    dateCell.appendChild(dateNode);
    idCell.appendChild(idNode);
    itemCell.appendChild(itemsNode);
    totalCell.appendChild(totalNode);
    statusCell.appendChild(statusNode);

    row.appendChild(dateCell);
    row.appendChild(idCell);
    row.appendChild(itemCell);
    row.appendChild(totalCell);
    row.appendChild(statusCell);

    purchaseHistoryTableBody.appendChild(row);
}

const filterUsers = function() {
    const filteredUsers = [];
    const searchString = searchInput.value.toLowerCase();
    for (let i = 0; i < users.length; i++) {
        const user = users[i];
        let added = false;
        if (user["id"].toString().includes(searchString) ||
            user["firstName"].toString().toLowerCase().includes(searchString) ||
            user["lastName"].toString().toLowerCase().includes(searchString)) {
            filteredUsers.push(user);
            added = true;
        }
        if (null !== user["address"] && !added) {
            if (user["address"]["addressLine"].toString().toLowerCase().includes(searchString)) {
                filteredUsers.push(user);
            }
        }
    }

    loadUsers(filteredUsers);
}


const setEventListeners = function () {
    editFullNameButton.addEventListener("click", editFullName);
    editEmailButton.addEventListener("click",editEmail);
    editAddressButton.addEventListener("click", editAddress);

    const closeButtons = document.getElementsByClassName("btn--close");
    for (let i = 0; i < closeButtons.length; i++) {
        closeButtons[i].addEventListener("click", function() {
            closeButtons[i].parentElement.parentElement.classList.remove("display");
        });
    }
}

const editCustomerSuccess = async function() {
    manageUser(user);
    await getUsers();
}

const editFullName = function() {
    document.getElementById("newFirstName").value = "";
    document.getElementById("newLastName").value = "";
    editFullNameOverlay.classList.add("display");
    document.getElementById("updateFullNameButton").addEventListener("click", updateFullName);
}
const updateFullName = function(event) {
    event.preventDefault();
    user["firstName"] = document.getElementById("newFirstName").value;
    user["lastName"] = document.getElementById("newLastName").value;
    sendApiRequest(USERS_API_PATHNAME + "/" + user["id"], "PUT",
        {firstName: user["firstName"], lastName: user["lastName"]},
        editCustomerSuccess, null);
    editFullNameOverlay.classList.remove("display");
}


const editEmail = function() {
    document.getElementById("newEmail").value = "";
    editEmailOverlay.classList.add("display");
    document.getElementById("updateEmailButton").addEventListener("click", updateEmail);
}

const updateEmail = function(event) {
    event.preventDefault();
    user["email"] = document.getElementById("newEmail").value;
    sendApiRequest(USERS_API_PATHNAME + "/" + user["id"], "PUT",
        {email: user["email"]},
        editCustomerSuccess, null);
    editEmailOverlay.classList.remove("display");
}

const editAddress = function() {
    document.getElementById("newAddress1").value = "";
    document.getElementById("newAddress2").value = "";
    document.getElementById("newPostalCode").value = "";
    document.getElementById("newCity").value = "";
    document.getElementById("newCountry").value = "";
    editAddressOverlay.classList.add("display");
    document.getElementById("updateAddressButton").addEventListener("click", updateAddress);
}

const updateAddress = function(event) {
    event.preventDefault();
    user["address"]["addressLine1"] = document.getElementById("newAddress1").value;
    user["address"]["addressLine2"] = document.getElementById("newAddress2").value;
    user["address"]["postalCode"] = document.getElementById("newPostalCode").value;
    user["address"]["city"] = document.getElementById("newCity").value;
    user["address"]["country"] = document.getElementById("newCountry").value;
    user["address"]["addressLine"] = buildAddressLine(user["address"]);
    sendApiRequest(USERS_API_PATHNAME + "/" + user["id"], "PUT",
        {addressLine1: user["address"]["addressLine1"],
            addressLine2: user["address"]["addressLine2"],
            city: user["address"]["city"],
            postalCode: user["address"]["postalCode"],
            country: user["address"]["country"]},
        editCustomerSuccess, null);
    editAddressOverlay.classList.remove("display");
}

const buildAddressLine = function(address) {
    let line = address["addressLine1"] + ", ";
    if (address["addressLine2"].length > 0) {
        line += address["addressLine2"] + ", ";
    }
    line += address["postalCode"] + " " + address["city"] + ", " + address["country"];
    return line;
}
