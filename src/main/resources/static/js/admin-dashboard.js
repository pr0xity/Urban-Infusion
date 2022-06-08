import { sendGetNewUsersRequest } from "./controllers/userController.js";
import { sendGetRecentOrdersRequest } from "./controllers/orderController.js";
import { sendGetRecentReviewsRequest } from "./controllers/reviewController.js";

/**
 * Initializes the page content.
 */
window.addEventListener("DOMContentLoaded", getTableContent);

/**
 * Populates all tables.
 */
function getTableContent() {
  getNewCustomers();
  getLatestOrders();
  getRecentReviews();
}

/**
 * Fetches new customers from server and populates the new customers table.
 */
function getNewCustomers() {
  sendGetNewUsersRequest().then((response) => loadCustomers(response));
}

/**
 * Populates the new customers table.
 *
 * @param users the users to be added to the table.
 */
function loadCustomers(users) {
  for (let i = 0; i < users.length; i++) {
    addCustomerRow(users[i]);
  }
}

/**
 * Shows a new customer as a row and adds it to the new customers table.
 *
 * @param user the user to be shown.
 */
function addCustomerRow(user) {
  if (!document.getElementById("customerTable")) return;
  const tableBody = document.getElementById("customerTableBody");
  const row = document.createElement("tr");

  const idCell = document.createElement("td");
  const nameCell = document.createElement("td");
  const emailCell = document.createElement("td");

  const idNode = document.createTextNode(user["id"]);
  const nameNode = document.createTextNode(user["firstName"] + " " + user["lastName"]);
  const emailNode = document.createTextNode(user["email"]);

  idCell.appendChild(idNode);
  nameCell.appendChild(nameNode);
  emailCell.appendChild(emailNode);

  row.appendChild(idCell);
  row.appendChild(nameCell);
  row.appendChild(emailCell);
  tableBody.appendChild(row);
}

/**
 * Fetches the latest orders from server and populates the recent orders table.
 */
function getLatestOrders() {
  sendGetRecentOrdersRequest().then((response) => loadOrders(response));
}

/**
 * Populates the recent orders table.
 */
function loadOrders(orders) {
  for (let i = 0; i < orders.length; i++) {
    const order = orders[i];
    addOrderRow(order);
  }
}

/**
 * Shows a recent order as a row and adds it to the recent orders table.
 *
 * @param order the order to be shown.
 */
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
