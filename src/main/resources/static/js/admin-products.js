const productTable = document.getElementById("productTable");
const tableBody = document.getElementById("productTableBody");
const overlay = document.getElementById("overlay");
const editNameButton = document.getElementById("editNameButton");
const editDescriptionButton = document.getElementById("editDescriptionButton");
const editPriceButton = document.getElementById("editPriceButton");
const editCategoryButton = document.getElementById("editCategoryButton");
const editNameOverlay = document.getElementById("editNameOverlay");
const editPriceOverlay = document.getElementById("editPriceOverlay");
const editDescriptionOverlay = document.getElementById("editDescriptionOverlay");
const editCategoryOverlay = document.getElementById("editCategoryOverlay");

let products = null;
let product = null;

const initializeProducts = function() {
    getProducts();
    setEventListeners();
}

const getProducts = function() {
    const req = new XMLHttpRequest();
    req.overrideMimeType("application/json");
    req.open('GET', URL + PRODUCT_API_PATHNAME, true);
    req.onload  = function() {
        products = JSON.parse(req.responseText);
        loadProducts(products)
    };
    req.send(null);
}

const loadProducts = function(products) {
    tableBody.innerHTML = "";
    for (let i = 0; i < products.length; i++) {
        const product = products[i];
        addProductRow(product, i);
    }
}

const addProductRow = function(product, productNumber) {
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

    const idNode = document.createTextNode(product["id"]);
    const nameNode = document.createTextNode(product["name"]);
    const categoryNode = document.createTextNode(product["category"]["name"]);
    const priceNode = document.createTextNode(product["price"]);
    const stockNode = document.createTextNode(product["inventory"]);

    idCell.appendChild(idNode);
    nameCell.appendChild(nameNode);
    categoryCell.appendChild(categoryNode);
    priceCell.appendChild(priceNode);
    stockCell.appendChild(stockNode);

    row.appendChild(idCell);
    row.appendChild(nameCell);
    row.appendChild(categoryCell);
    row.appendChild(priceCell);
    row.appendChild(stockCell);
    tableBody.appendChild(row);
}

const manageProduct = function(product) {
    document.getElementById("updateNameButton").dataset.productId = product.id;
    const idLabel = document.getElementById("productIdLabel");
    const nameLabel = document.getElementById("productNameLabel");
    const stockLabel = document.getElementById("productStockLabel");
    const priceLabel = document.getElementById("productPriceLabel");
    const categoryLabel = document.getElementById("productCategoryLabel");
    const descriptionLabel = document.getElementById("productDescriptionLabel");
    this.product = product;



    idLabel.textContent = product["id"];
    nameLabel.textContent = product["name"];
    stockLabel.textContent = product["inventory"];
    priceLabel.textContent = product["price"];
    categoryLabel.textContent = product["category"]["name"];
    descriptionLabel.innerHTML = product["description"].replaceAll(".", ".&ZeroWidthSpace;");
    fetchImage(product["id"]);
}

const fetchImage = function(productId) {
    const req = new XMLHttpRequest();
    req.open('GET', URL + IMAGE_API_PATHNAME + "/" + productId, true);
    req.responseType = "blob";
    req.onload  = function() {
        const urlCreator = window.URL || window.webkitURL;
        const imageUrl = urlCreator.createObjectURL(this.response);
        const image = document.getElementById("productImage");
        image.src = imageUrl;
        overlay.classList.remove("hidden");
    };
    req.send(null);
}

const setEventListeners = function() {
    editNameButton.addEventListener("click",function() {
        editName();
    });
    editDescriptionButton.addEventListener("click",function() {
        editDescription();
    });
    editPriceButton.addEventListener("click",function() {
        editPrice();
    });
    editCategoryButton.addEventListener("click",function() {
        editCategory();
    });
}

const button = document.getElementById("updateNameButton");

const updateProductName = function(event) {
    event.preventDefault();
    const newName = document.getElementById("newName").value;
    if (newName.length > 0) {
        sendApiRequest(`${PRODUCT_API_PATHNAME}/${button.dataset.productId}`, "PUT", { name: newName }, editProductSuccess);
    }
}

const updateProductPrice = function(event) {
    event.preventDefault();
    const newPrice = document.getElementById("newPrice").value;
    if (newPrice.length > 0) {
        sendApiRequest(`${PRODUCT_API_PATHNAME}/${button.dataset.productId}`, "PUT", { price: newPrice }, editProductSuccess);
    }
}

const updateProductDescription = function(event) {
    event.preventDefault();
    const newDescription = document.getElementById("newDescription").value;
    if (newDescription.length > 0) {
        sendApiRequest(`${PRODUCT_API_PATHNAME}/${button.dataset.productId}`, "PUT", { description: newDescription }, editProductSuccess);
    }
}

const updateProductCategory = function(event) {
    event.preventDefault();
    const newCategory = document.getElementById("newCategory").value;
    if (newCategory.length > 0) {
        sendApiRequest(`${PRODUCT_API_PATHNAME}/${button.dataset.productId}`, "PUT", { categoryName: newCategory }, editProductSuccess);
    }
}

const editProductSuccess = function() {
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
}

const editName = function() {
    overlay.querySelector("[data-product-id]").addEventListener("click", updateProductName);
    editNameOverlay.classList.add("display");
}

const editDescription = function() {
    document.getElementById("updateDescriptionButton").addEventListener("click", updateProductDescription);
    editDescriptionOverlay.classList.add("display");
}

const editPrice = function() {
    document.getElementById("updatePriceButton").addEventListener("click", updateProductPrice);
    editPriceOverlay.classList.add("display");
}

const editCategory = function() {
    document.getElementById("updateCategoryButton").addEventListener("click", updateProductCategory);
    editCategoryOverlay.classList.add("display");
}

if (document.getElementsByClassName("edit__window")) {
    window.addEventListener('mouseup', function(event) {

        const editOverlays = document.getElementsByClassName("edit__window");
        for (let i = 0; i < editOverlays.length; i++) {
            if (editOverlays[i].contains(event.target)) {
                return;
            }
        }
        hideEditOverlays();
    });
}

const filterProducts = function() {
    const filteredProducts = [];
    const searchString = searchInput.value.toLowerCase();
    for (let i = 0; i < products.length; i++) {
        const product = products[i];
        if (product["id"].toString().includes(searchString) ||
            product["name"].toLowerCase().includes(searchString) ||
            product["category"]["name"].toLowerCase().includes(searchString)) {
            filteredProducts.push(product);
        }
    }
    loadProducts(filteredProducts);
}

const fileSelector = document.getElementById('file-selector');
fileSelector.addEventListener('change', (event) => {
    const file = event.target.files[0];
    readImage(file);
    uploadImage(file);
});

const readImage = function(file) {
    const reader = new FileReader();
    reader.addEventListener('load', (event) => {
        document.getElementById("productImage").src = event.target.result;
    });
    reader.readAsDataURL(file);
}

const uploadImage = function(imageFile) {
    let data = new FormData();
    data.append("file", imageFile);
    uploadToServer(data);
}

const uploadToServer = function(data) {
    const xhr = new XMLHttpRequest();
    xhr.open("PUT", `${IMAGE_API_PATHNAME}/${button.dataset.productId}`, true);

    xhr.send(data);
}