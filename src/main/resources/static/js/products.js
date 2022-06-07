import { setRatingLeavesOnProductsAndReviews } from "./views/reviewLeavesView.js";
import { sendGetAllActiveProductsRequest, sendGetOneProductCategoryRequest, sendGetProductCategoriesRequest } from "./controllers/productController.js";
import { addProductToProductCard, implementProductCardFunctionality, setReviewCount, setWishlistButtonOnProductCards } from "./views/productCardView.js";
import {setIncrementCounter} from "./controllers/cartController.js";

const productsContainer = document.querySelector(".products__container");
let allProducts = await sendGetAllActiveProductsRequest();
let allProductCards;

/**
 * Empties the product container.
 */
const emptyProductContainer = function () {
  productsContainer.replaceChildren();
};

/**
 * Requests products form the category of the button clicked and
 * places them in the products' container.
 *
 * @param event click event on a category button.
 */
const getAndSetFromCategory = function (event) {
  event.preventDefault();
  document.querySelectorAll(".category__btn").forEach((button) => button.classList.remove("category__btn--active"));
  event.target.classList.add("category__btn--active");
  const categoryId = event.target.dataset.categoryid;
  const productsList = [];

  sendGetOneProductCategoryRequest(categoryId).then((category) => {
    allProducts.forEach((product) => {
      if (product.category.name === category.name) {
        productsList.push(product);
      }
    });
    setProductsToProductCards(productsList);
    implementProductCardFunctionality();
  });
};

/**
 * Requests all products and places them in the product container.
 *
 * @param event click even on all products button.
 */
const getAndSetAllProducts = function (event) {
  event.preventDefault();
  document
    .querySelectorAll(".category__btn")
    .forEach((button) => button.classList.remove("category__btn--active"));
  event.target.classList.add("category__btn--active");
  setProductsToProductCards(allProducts)
  allProductCards = document.querySelectorAll(".product-card");
  implementProductCardFunctionality();
};

/**
 * Empties the products container and places the given list of
 * products in the container.
 *
 * @param products the list of products to place in the products' container.
 */
const setProductsToProductCards = function (products) {
  emptyProductContainer();
  products.forEach((product) => {
    addProductCardToPage(addProductToProductCard(product));
    setReviewCount(product);
  });
  setWishlistButtonOnProductCards();
  implementProductCardFunctionality();
  setRatingLeavesOnProductsAndReviews();
};

/**
 * Adds the given html of a product card to the product cart container.
 *
 * @param productCard the html of product card to add.
 */
const addProductCardToPage = function (productCard) {
  if (typeof productCard === "string") {
    productsContainer.insertAdjacentHTML("afterbegin", productCard);
  } else {
    productsContainer.appendChild(productCard);
  }
};

/**
 * Gets the categories from the tea shop and formats and places them on the site.
 */
const setCategoryButton = function () {
  const searchSection = document.querySelector(".products__box--search");
  searchSection.insertAdjacentHTML(
    "beforeend",
    `<button class="btn btn--text category__btn" id="All">All products</button>`
  );

  sendGetProductCategoriesRequest().then(categories => {
    categories.forEach(category => {
      searchSection.insertAdjacentHTML("beforeend", createCategoryButton(category));
    });
    const categoryButtons = document.querySelectorAll(".category__btn");
    addEventListenerToCategoryButtons(categoryButtons);
  });
};

/**
 * Creates the html for category button from the given category.
 *
 * @param category the category to make a category button from.
 * @return {string} the html of the category button.
 */
const createCategoryButton = function (category) {
  return `<button class="btn btn--text category__btn" data-categoryid="${category.id}" id="${category.name}">${category.name}</button>`;
};

/**
 * Adds event listeners to the given category buttons, sends request to
 * get products from their category.
 *
 * @param buttons the buttons to add event listeners to.
 */
const addEventListenerToCategoryButtons = function (buttons) {
  buttons.forEach((button) => {
    if (button.id !== "All") {
      button.addEventListener("click", getAndSetFromCategory);
    }
  });
};

/**
 * Sets event listener to the search bar.
 */
const setSearchBar = function () {
  const searchForm = document.querySelector(".products__form--search");
  const searchBar = document.querySelector(".products__input--search");
  const searchButton = document.querySelector("#btn-search");

  /**
   * Sends a request for all products and searched for the products matching
   * the given search string. Will empty the product container of replace it with
   * the products found.
   *
   * @param searchString the string to search for matching products of.
   */
  const sendSearchRequest = function (searchString) {
    const productsFound = [];
    allProductCards.forEach((product) => {
      let productInfo = product.textContent;

      if (productInfo !== null && productInfo !== undefined) {
        productInfo = productInfo.toString();

        if (productInfo.toLowerCase().includes(searchString) && !productsFound.includes(product)) {
          console.log(typeof product)
          productsFound.push(product);
        }
      }
    });
    emptyProductContainer();
    productsFound.forEach(product => addProductCardToPage(product));
  };

  /**
   * Searches after the products which matches the search string from the event.
   * A search will happen for every 3rd letter in the input.
   *
   * @param event event form search input.
   */
  const automaticSearchWhenTyping = function (event) {
    event.preventDefault();
    const searchString = event.target.value.toLowerCase();
    if (searchString === "") {
      emptyProductContainer();
      allProductsButton.click();
    }
    sendSearchRequest(searchString);
  };

  /**
   * Searches when triggered, takes input from search bar.
   */
  const manualSearch = function (event) {
    event.preventDefault();
    const searchString = searchBar.value;
    sendSearchRequest(searchString);
  };

  searchForm.addEventListener("submit", (event) => event.preventDefault());
  searchBar.addEventListener("input", automaticSearchWhenTyping);
  searchButton.addEventListener("click", manualSearch);
};
setSearchBar();
setCategoryButton();
setIncrementCounter().finally();

const allProductsButton = document.querySelector("#All");
allProductsButton.addEventListener("click", getAndSetAllProducts);
allProductsButton.click();
