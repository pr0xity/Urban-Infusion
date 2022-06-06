import { sendGetNewUsersRequest } from "./controllers/userController.js";
import { sendGetRecentOrdersRequest } from "./controllers/orderController.js";
import { sendGetRecentReviewsRequest } from "./controllers/reviewController.js";

window.addEventListener("DOMContentLoaded", getTableContent);

function getTableContent() {
  getNewCustomers();
  getLatestOrders();
  getRecentReviews();
}

function getNewCustomers() {
  sendGetNewUsersRequest().then((response) => loadCustomers(response));
}

function loadCustomers(users) {
  for (let i = 0; i < users.length; i++) {
    const user = users[i];
    addCustomerRow(
      user["id"],
      user["firstName"] + " " + user["lastName"],
      user["email"]
    );
  }
}

function addCustomerRow(id, name, email) {
  if (!document.getElementById("customerTable")) return;
  const tableBody = document.getElementById("customerTableBody");
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

function getLatestOrders() {
  sendGetRecentOrdersRequest().then((response) => loadOrders(response));
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

  const dateCell = document.createElement("td");
  const orderIdCell = document.createElement("td");
  const customerIdCell = document.createElement("td");
  const statusCell = document.createElement("td");

  let string = order["createdAt"];
  let trimmedString = string.substring(0, 10);
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

/**
 * Sends request to get the recent reviews and loads them in the review table.
 */
function getRecentReviews() {
  sendGetRecentReviewsRequest().then((response) => loadReviews(response));
}

/**
 * Displays the given reviews in the review table.
 *
 * @param reviews reviews to display.
 */
function loadReviews(reviews) {
  const reviewTable = document.querySelector("#ratingTable");
  reviews.forEach((review) => {
    reviewTable.insertAdjacentHTML("beforeend", createReviewRow(review));
  });
}

/**
 * Formats and returns a html row of the given review.
 *
 * @param review the review to create html row of.
 * @return {string} html of the review in a table row.
 */
function createReviewRow(review) {
  return `
      <tr>
        <td>${review.updatedAt.substring(0, 10)}</td>
        <td>${review.product.id}</td>
        <td>${review.user.id}</td>
        <td>${review.displayName}</td>
        <td>${review.rating}</td>
        <td>${review.comment}</td>
      </tr>`;
}
