import {
  sendGetAllOrdersRequest,
  sendUpdateOrderRequest,
} from "./controllers/orderController.js";

let orders = null;
const tableBody = document.getElementById("orderTableBody");
const manageOrderTableBody = document.getElementById("manageOrderTableBody");
const overlay = document.getElementById("overlay");
const processedCheckBox = document.getElementById("orderProcessed");

/**
 * Initializes the page content.
 */
function initializeOrders() {
  getOrders();
}

/**
 * Fetches all orders from the server and displays them in the order table.
 *
 * @returns {Promise<void>}
 */
async function getOrders() {
  orders = await sendGetAllOrdersRequest();
  loadOrders(orders);
}

/**
 * Filters the order table to display only orders that match the input in the search bar.
 */
function filterOrders() {
  const filteredOrders = [];
  const searchString = document.getElementById("searchInput").value.toLowerCase();
  for (let i = 0; i < orders.length; i++) {
    const order = orders[i];
    let added = false;
    if (
      order["id"].toString().includes(searchString) ||
      order["user"]["id"].toString().includes(searchString)
    ) {
      filteredOrders.push(order);
      added = true;
    }
    for (let j = 0; j < order["orderItems"].length; j++) {
      const item = order["orderItems"][j];
      if (
        item["product"]["name"].toLowerCase().includes(searchString) &&
        !added
      ) {
        filteredOrders.push(order);
        break;
      }
    }
  }
  loadOrders(filteredOrders);
}

// Adds functionality to the search bar.
document.querySelector("#searchInput").addEventListener("input", filterOrders);

/**
 * Populates the order table with given orders.
 *
 * @param orders orders to be added to table.
 */
function loadOrders(orders) {
  tableBody.innerHTML = "";
  for (let i = 0; i < orders.length; i++) {
    const order = orders[i];
    addOrderRow(order);
  }
}

/**
 * Shows an order as a row in the order table.
 *
 * @param order the order to be shown.
 */
function addOrderRow(order) {
  if (!document.getElementById("orderTable")) return;
  const row = document.createElement("tr");
  row.addEventListener("click", () => {
    manageOrder(order);
    overlay.classList.toggle("hidden");
  });

  const dateCell = document.createElement("td");
  const orderIdCell = document.createElement("td");
  const customerIdCell = document.createElement("td");
  const itemsCell = document.createElement("td");
  const statusCell = document.createElement("td");

  let string = order["createdAt"];
  let trimmedString = string.substring(0, 10);
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
  const itemsNode = document.createTextNode(items);
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

/**
 * Sets the information in the manage overlay and shows it to the user.
 *
 * @param order the order to be managed.
 */
function manageOrder(order) {
  const orderNameLabel = document.getElementById("orderNameLabel");
  const orderEmailLabel = document.getElementById("orderEmailLabel");
  const orderAddressLabel = document.getElementById("orderAddressLabel");
  const orderDateLabel = document.getElementById("orderDateLabel");
  const orderIdLabel = document.getElementById("orderIdLabel");

  orderNameLabel.textContent =
    order["user"]["firstName"] + " " + order["user"]["lastName"];
  orderEmailLabel.textContent = order["user"]["email"];
  let address = order["user"]["address"]["addressLine"];
  orderAddressLabel.innerHTML = address.replaceAll(",", ",&ZeroWidthSpace;");
  let string = order["createdAt"];
  orderDateLabel.textContent = string.substring(0, 10);
  orderIdLabel.textContent = order["id"];

  processedCheckBox.onclick = function () {
    if (!overlay.classList.contains("hidden")) {
      updateOrder(order);
    }
  };

  loadOrderItems(order);

  processedCheckBox.checked = order["processed"] === true;
}

/**
 * Sends an update order request to the server and repopulates the order table with the updated orders.
 *
 * @param order the order to be updated.
 */
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

  sendUpdateOrderRequest(`${order.id}`, dto, getOrders).finally();
}

/**
 * Populates the order items table with the items in the given order.
 * @param order order to show the order items for.
 */
function loadOrderItems(order) {
  const items = order["orderItems"];
  manageOrderTableBody.innerHTML = "";
  for (let i = 0; i < items.length; i++) {
    const item = items[i];
    addOrderItemRow(item);
  }
}

/**
 * Shows an order item as a row in the order items table.
 *
 * @param item the item to be shown.
 */
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

initializeOrders();
