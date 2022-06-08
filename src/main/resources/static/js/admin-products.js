import {
  sendAddProductImageRequest,
  sendAddProductRequest,
  sendGetAllProductsRequest,
  sendGetProductImageRequest,
  sendUpdateProductImageRequest,
  sendUpdateProductRequest,
} from "./controllers/productController.js";
import { hideEditOverlays } from "./admin.js";
import {
  getProductIdFromElement,
  reloadCurrentPage,
  showElement,
} from "./tools.js";
import { LEAF_UNSELECTED } from "./views/reviewLeavesView.js";

const productTable = document.getElementById("productTable");
const tableBody = document.getElementById("productTableBody");
const overlay = document.getElementById("overlay");
const addProductOverlay = document.getElementById("addProductOverlay");
const addProductButton = document.getElementById("addNewProductButton");
const editNameButton = document.getElementById("editNameButton");
const editDescriptionButton = document.getElementById("editDescriptionButton");
const editPriceButton = document.getElementById("editPriceButton");
const editCategoryButton = document.getElementById("editCategoryButton");
const editNameOverlay = document.getElementById("editNameOverlay");
const editPriceOverlay = document.getElementById("editPriceOverlay");
const editDescriptionOverlay = document.getElementById(
  "editDescriptionOverlay"
);
const editCategoryOverlay = document.getElementById("editCategoryOverlay");
const activeStatusCheckBox = document.getElementById("setAsInactive");

let products = null;
let product = null;

/**
 * Retrieves the current products in the database and adds event listeners to buttons on the page
 */
const initializeProducts = function () {
  getProducts();
  setEventListeners();
};

document.addEventListener("DOMContentLoaded", initializeProducts);

/**
 * Retrieves the current products in the database and loads them into the table
 */
const getProducts = async function () {
  products = await sendGetAllProductsRequest().finally();
  loadProducts(products);
};

/**
 * Loads products into the table
 */
const loadProducts = function (products) {
  tableBody.innerHTML = "";
  for (let i = 0; i < products.length; i++) {
    const product = products[i];
    addProductRow(product, i);
  }
};

/**
 * Creates a new table row and inserts a given product to the row
 * @param product the product to add
 * @param productNumber the index of the row
 */
const addProductRow = function (product, productNumber) {
  if (!document.getElementById("productTable")) return;
  const row = document.createElement("tr");
  row.dataset.productNumber = productNumber;

  if (null != overlay) {
    row.onclick = () => {
      manageProduct(product);
    };
  }

  const idCell = document.createElement("td");
  const nameCell = document.createElement("td");
  const categoryCell = document.createElement("td");
  const priceCell = document.createElement("td");
  const stockCell = document.createElement("td");
  const statusCell = document.createElement("td");

  const idNode = document.createTextNode(product["id"]);
  const nameNode = document.createTextNode(product["name"]);
  const categoryNode = document.createTextNode(product["category"]["name"]);
  const priceNode = document.createTextNode(product["price"]);
  const stockNode = document.createTextNode(product["inventory"]);

  let statusNode = document.createTextNode("Active");
  if (product["inactive"] === true) {
    statusNode = document.createTextNode("Inactive");
  }

  idCell.appendChild(idNode);
  nameCell.appendChild(nameNode);
  categoryCell.appendChild(categoryNode);
  priceCell.appendChild(priceNode);
  stockCell.appendChild(stockNode);
  statusCell.appendChild(statusNode);

  row.appendChild(idCell);
  row.appendChild(nameCell);
  row.appendChild(categoryCell);
  row.appendChild(priceCell);
  row.appendChild(stockCell);
  row.appendChild(statusCell);
  tableBody.appendChild(row);
};

const accountFormAlerts = document.querySelectorAll(".account__form--alert");

/**
 * Sets the alert on the form to the given alert message.
 *
 * @param alertMessage the message to set as alert.
 */
const setAccountFormAlert = function (alertMessage) {
  accountFormAlerts.forEach((alert) => (alert.innerHTML = `${alertMessage}`));
};

/**
 * When a product row is clicked on, this method initializes the information from the clicked product into the overlay
 * @param product the product that was clicked
 */
const manageProduct = function (product) {
  document.getElementById("updateNameButton").dataset.productid = product.id;
  const idLabel = document.getElementById("productIdLabel");
  const nameLabel = document.getElementById("productNameLabel");
  const stockLabel = document.getElementById("productStockLabel");
  const priceLabel = document.getElementById("productPriceLabel");
  const categoryLabel = document.getElementById("productCategoryLabel");
  const descriptionLabel = document.getElementById("productDescriptionLabel");

  idLabel.textContent = product["id"];
  nameLabel.textContent = product["name"];
  stockLabel.textContent = product["inventory"];
  priceLabel.textContent = product["price"];
  categoryLabel.textContent = product["category"]["name"];
  descriptionLabel.innerHTML = product["description"].replaceAll(
    ".",
    ".&ZeroWidthSpace;"
  );
  fetchImage(product["imageId"]);

  activeStatusCheckBox.onclick = function () {
    if (!overlay.classList.contains("hidden")) {
      updateActiveStatus();
    }
  };
  activeStatusCheckBox.checked = product["inactive"] === true;
};

/**
 * Changes the active status for a product
 */
const updateActiveStatus = function () {
  const productId = getProductIdFromElement(button);
  if (activeStatusCheckBox.checked) {
    sendUpdateProductRequest(
      productId,
      { inactive: true },
      editProductSuccess
    ).finally();
  } else {
    sendUpdateProductRequest(
      productId,
      { inactive: false },
      editProductSuccess
    ).finally();
  }
};

/**
 * Gets an image from the database with a given id
 * @param imageId the id of the image
 */
const fetchImage = function (imageId) {
  if (imageId !== 0) {
    sendGetProductImageRequest(imageId).then((response) => {
      if (response !== undefined) {
        const urlCreator = window.URL || window.webkitURL;
        const imageUrl = urlCreator.createObjectURL(response);
        const image = document.getElementById("productImage");
        image.src = imageUrl;
      } else {
        const image = document.getElementById("productImage");
        image.src = LEAF_UNSELECTED;
      }
      showElement(overlay);
    });
  } else {
    const image = document.getElementById("productImage");
    image.src = LEAF_UNSELECTED;
    showElement(overlay);
  }
};

/**
 * Adds event listeners to buttons on the page
 */
const setEventListeners = function () {
  editNameButton.addEventListener("click", editName);
  editDescriptionButton.addEventListener("click", editDescription);
  editPriceButton.addEventListener("click", editPrice);
  editCategoryButton.addEventListener("click", editCategory);
};

const button = document.getElementById("updateNameButton");

/**
 * Changes the name of a product
 */
const updateProductName = function (event) {
  event.preventDefault();
  const productId = getProductIdFromElement(button);
  const newName = document.getElementById("newName").value;
  if (newName.length > 0) {
    sendUpdateProductRequest(
      productId,
      { name: newName },
      editProductSuccess
    ).finally();
  }
};

/**
 * Changes the price of a product
 */
const updateProductPrice = function (event) {
  event.preventDefault();
  const productId = getProductIdFromElement(button);
  const newPrice = document.getElementById("newPrice").value;
  if (newPrice.length > 0) {
    sendUpdateProductRequest(
      productId,
      { price: newPrice },
      editProductSuccess
    ).finally();
  }
};

/**
 * Changes the description of a product
 */
const updateProductDescription = function (event) {
  event.preventDefault();
  const productId = getProductIdFromElement(button);
  const newDescription = document.getElementById("newDescription").value;
  if (newDescription.length > 0) {
    sendUpdateProductRequest(
      productId,
      { description: newDescription },
      editProductSuccess
    ).finally();
  }
};

/**
 * Changes the category of a product
 */
const updateProductCategory = function (event) {
  event.preventDefault();
  const productId = getProductIdFromElement(button);
  const newCategory = document.getElementById("newCategory").value;
  if (newCategory.length > 0) {
    sendUpdateProductRequest(
      productId,
      { category: newCategory },
      editProductSuccess
    ).finally();
  }
};

/**
 * This method is called when a product has been successfully updated
 */
const editProductSuccess = function () {
  hideEditOverlays();
  getProducts();

  const nameLabel = document.getElementById("productNameLabel");
  const priceLabel = document.getElementById("productPriceLabel");
  const categoryLabel = document.getElementById("productCategoryLabel");
  const descriptionLabel = document.getElementById("productDescriptionLabel");

  const newName = document.getElementById("newName").value;
  const newDescription = document.getElementById("newDescription").value;
  const newCategory = document.getElementById("newCategory").value;
  const newPrice = document.getElementById("newPrice").value;

  if (newName !== "") {
    nameLabel.textContent = newName;
  }
  if (newDescription !== "") {
    descriptionLabel.textContent = newDescription;
  }
  if (newCategory !== "") {
    categoryLabel.textContent = newCategory;
  }
  if (newPrice !== "") {
    priceLabel.textContent = newPrice;
  }
};

/**
 * Shows the edit name-overlay when the edit name-button is clicked in the edit product-overlay
 */
const editName = function () {
  document
    .getElementById("updateNameButton")
    .addEventListener("click", updateProductName);
  editNameOverlay.classList.add("display");
};

/**
 * Shows the edit description-overlay when the edit description-button is clicked in the edit product-overlay
 */
const editDescription = function () {
  document
    .getElementById("updateDescriptionButton")
    .addEventListener("click", updateProductDescription);
  editDescriptionOverlay.classList.add("display");
};

/**
 * Shows the edit price-overlay when the edit price-button is clicked in the edit product-overlay
 */
const editPrice = function () {
  document
    .getElementById("updatePriceButton")
    .addEventListener("click", updateProductPrice);
  editPriceOverlay.classList.add("display");
};

/**
 * Shows the edit category-overlay when the edit category-button is clicked in the edit product-overlay
 */
const editCategory = function () {
  document
    .getElementById("updateCategoryButton")
    .addEventListener("click", updateProductCategory);
  editCategoryOverlay.classList.add("display");
};

if (document.getElementsByClassName("edit__window")) {
  window.addEventListener("mouseup", function (event) {
    const editOverlays = document.getElementsByClassName("edit__window");
    for (let i = 0; i < editOverlays.length; i++) {
      if (editOverlays[i].contains(event.target)) {
        return;
      }
    }
    hideEditOverlays();
  });
}

/**
 * Filters the products in the table when a user types something in the search-field.
 */
const filterProducts = function () {
  const filteredProducts = [];
  const searchString = this.value.toLowerCase();
  for (let i = 0; i < products.length; i++) {
    const product = products[i];
    if (
      product["id"].toString().includes(searchString) ||
      product["name"].toLowerCase().includes(searchString) ||
      product["category"]["name"].toLowerCase().includes(searchString)
    ) {
      filteredProducts.push(product);
    }
  }
  loadProducts(filteredProducts);
};
document
  .querySelector("#searchInput")
  .addEventListener("input", filterProducts);

const fileSelector = document.getElementById("file-selector");

fileSelector.addEventListener("change", (event) => {
  const file = event.target.files[0];
  readImage(file);
  uploadImage(file);
});

/**
 * Gets a file's data as a base64 encoded string
 * @param file the file to read
 */
const readImage = function (file) {
  const reader = new FileReader();
  reader.addEventListener("load", (event) => {
    document.getElementById("productImage").src = event.target.result;
  });
  reader.readAsDataURL(file);
};

/**
 * Creates a Form and pushes it to the server
 * @param imageFile the file to push
 */
const uploadImage = function (imageFile) {
  let data = new FormData();
  data.append("imageFile", imageFile);
  uploadToServer(data);
};

/**
 * Uploads an image to the database
 * @param data the image to upload
 */
const uploadToServer = function (data) {
  const productId = getProductIdFromElement(button);
  sendUpdateProductImageRequest(
    productId,
    data,
    editProductSuccess
  ).finally();
};

if (null != overlay) {
  addProductButton.onclick = () => {
    addProductOverlay.classList.remove("hidden");
  };
}

/**
 * Gets data from a specific product
 * @returns {{price: *, name: *, description: *, inventory: *, category: *}}
 */
const getProductData = function () {
  const name = document.querySelector(`input[name="productName"]`).value;
  const category = document.querySelector(
    `input[name="productCategory"]`
  ).value;
  const price = document.querySelector(`input[name="productPrice"]`).value;
  const description = document.querySelector(
    `input[name="productDescription"]`
  ).value;
  const inventory = document.querySelector(`input[name="productStock"]`).value;
  return {
    name: name,
    description: description,
    price: price,
    inventory: inventory,
    category: category,
  };
};

/**
 * Checks whether the form in the add-product-overlay is correctly filled in
 * @returns true if it's correct, false if not
 */
const isFormValid = function () {
  const name = document.querySelector(`input[name="productName"]`).value;
  const category = document.querySelector(
    `input[name="productCategory"]`
  ).value;
  const price = document.querySelector(`input[name="productPrice"]`).value;
  const description = document.querySelector(
    `input[name="productDescription"]`
  ).value;
  const inventory = document.querySelector(`input[name="productStock"]`).value;

  if (name === "" || category === "" || description === "") {
    return false;
  }
  return !(isNaN(price) || isNaN(inventory));
};

/**
 * Gets the correct alert message if the form is invalid
 * @returns a string with the alert message
 */
const getAlertMessage = function () {
  const name = document.querySelector(`input[name="productName"]`).value;
  const category = document.querySelector(
    `input[name="productCategory"]`
  ).value;
  const price = document.querySelector(`input[name="productPrice"]`).value;
  const description = document.querySelector(
    `input[name="productDescription"]`
  ).value;
  const inventory = document.querySelector(`input[name="productStock"]`).value;

  let alertMessage = "";
  if (name === "" || category === "" || description === "") {
    alertMessage = "All fields need to be filled in";
  }
  if (isNaN(price)) {
    alertMessage = "The price must be a number";
  }
  if (isNaN(inventory)) {
    alertMessage = "Stock must be a number";
  }

  return alertMessage;
};

const imageSelector = document.getElementById("image-selector");
let image2 = document.getElementById("addProductImage");
let file = "";
let imageId;

imageSelector.addEventListener("change", async (event) => {
  file = event.target.files[0];
  readImage2(file);
  let data = new FormData();
  data.append("imageFile", file);
  imageId = await sendAddProductImageRequest(data).finally();
});

/**
 * Gets a file's data as a base64 encoded string
 * @param file the file to read
 */
const readImage2 = function (file) {
  const reader = new FileReader();
  reader.addEventListener("load", (event) => {
    image2.src = event.target.result;
  });
  reader.readAsDataURL(file);
};

/**
 * Adds a product to the databse
 */
const addProductRequest = async function (event) {
  event.preventDefault();
  if (isFormValid()) {
    const productData = getProductData();
    if (imageId !== 0) {
      productData.imageId = imageId;
    }
    sendAddProductRequest(productData).finally();
  } else {
    setAccountFormAlert(getAlertMessage());
  }
};

/*TODO: add-product-overlayen skal lukkes når submit blir trykket på */

const submitNewProductButton = document.getElementById(
  "submitNewProductButton"
);
submitNewProductButton.addEventListener("click", addProductRequest);
