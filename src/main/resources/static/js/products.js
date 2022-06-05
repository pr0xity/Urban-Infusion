import {setRatingLeavesOnProductsAndReviews} from "views/reviewLeavesView.js";

const setProductsPage = function () {
  const productsContainer = document.querySelector(".products__container");

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
    document
      .querySelectorAll(".filter")
      .forEach((button) => button.classList.remove("filter--active"));
    event.target.classList.add("filter--active");
    const categoryId = event.target.dataset.categoryid;
    const productsList = [];
    getJSON(`${URL}${PRODUCT_CATEGORY_API_PATHNAME}/${categoryId}`).then(
      (category) => {
        getJSON(`${URL}${PRODUCT_API_PATHNAME}`).then((products) => {
          products.forEach((product) => {
            if (product.category.name === category.name) {
              productsList.push(product);
            }
          });
          setProductsToProductCards(productsList);
        });
      }
    );
  };

  /**
   * Requests all products and places them in the product container.
   *
   * @param event click even on all products button.
   */
  const getAndSetAllProducts = function (event) {
    event.preventDefault();
    document
      .querySelectorAll(".filter")
      .forEach((button) => button.classList.remove("filter--active"));
    event.target.classList.add("filter--active");
    getJSON(`${URL}${PRODUCT_API_PATHNAME}`).then((response) => {
      setProductsToProductCards(response);
    });
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
    setAddToCartButtons();
    setRatingLeavesOnProductsAndReviews();
  };

  /**
   * Checks if the product is in the user's wishlist and formats the button
   * accordingly.
   */
  const setWishlistButtonOnProductCards = function () {
    getJSON(`${URL}${WISHLIST_API_PATHNAME}`).then((response) => {
      const productsInWishlist = response.products;
      const allProductCards = document.querySelectorAll(".product-card");
      allProductCards.forEach((productCard) => {
        productsInWishlist.forEach((product) => {
          if (product.id === Number(getProductIdFromElement(productCard))) {
            const wishlistButton = productCard.querySelector(
              ".product__btn--wishlist"
            );
            wishlistButton.dataset.wishlist = "true";
          }
          setWishlistButtons();
        });
      });
    });
    setWishlistButtons();
  };

  /**
   * Retrieves the number of rating the given product has and places it in the product card.
   *
   * @param product the product to find and set review counter on the product card for.
   */
  const setReviewCount = function (product) {
    getJSON(
      `${URL}${RATING_API_PATHNAME}${PRODUCT_PATHNAME}/${product.id}`
    ).then((response) => {
      const allProductCards = document.querySelectorAll(".product-card");
      allProductCards.forEach((productCard) => {
        if (product.id === Number(getProductIdFromElement(productCard))) {
          const reviewCounter = productCard.querySelector(
            ".product__rating > p"
          );
          reviewCounter.innerHTML = `(${response.length})`;
        }
      });
    });
  };

  /**
   * Adds the given product card to the product container.
   * @param productCard
   */
  const addProductCardToPage = function (productCard) {
    productsContainer.insertAdjacentHTML("afterbegin", productCard);
  };

  /**
   * Formats the given button in the product card and returns the html
   * of the formatted product card.
   *
   * @param product the product to place in a product card.
   * @return {string} html string of the product in the product card.
   */
  const addProductToProductCard = function (product) {
    return `<article class="product-card container--white" data-productid=${
      product.id
    }
    ><img 
      class="product-card__img"
      src="/api/products/images/${product.id}"
      alt="${product.name}"
    />
    <div class="product-card__details">
        <a
          href="/products/${product.id}"
          class="product__rating"
          data-rating=${product.averageRating}
          aria-label="This product has an average rating of ${
            product.averageRating
          } tea leaves, click to see all reviews'"
        ><img
           class="product__rating--img"
           src="../img/icons/leaf.svg"
           alt=""
           data-rating="1"
        /><img
          class="product__rating--img"
          src="../img/icons/leaf.svg"
          alt=""
          data-rating="2"
        /><img
          class="product__rating--img"
          src="../img/icons/leaf.svg"
          alt=""
          data-rating="3"
        /><img
          class="product__rating--img"
          src="../img/icons/leaf.svg"
          alt=""
          data-rating="4"
        /><img
          class="product__rating--img"
          src="../img/icons/leaf.svg"
          alt=""
          data-rating="5"
        />
        <p></p>
        </a>
        <a href="/products/${product.id}" class="link">
            <h4 class="product-card__heading">${product.name}</h4>
        </a>
        <ul class="product-card__list">
        ${formatDescriptionList(product.description)}
        </ul>
        <div class="product-card__cta">
            <p class="product-card__price">${product.price},-</p>
            <div
              class="product__btn--wishlist" data-wishlist="false"
            ></div>
            <button
              data-productid=${product.id}
              class="product__btn--add-to-cart"
              aria-label="add item to shopping cart"
            ><i class="ph-tote-light btn--icon"></i>
            </button>
        </div>
    </div>`;
  };

  /**
   * Formats the description list of the product and returns html of ti.
   *
   * @param description the description list of the product to format.
   * @return {string} the html string of the formatted description.
   */
  const formatDescriptionList = function (description) {
    let descriptionInHtml = "";
    description.split(".").forEach((line) => {
      descriptionInHtml += `<li class="product-card__item"><i class="ph-check-thin product-card__icon"></i>${line}</li>`;
    });
    return descriptionInHtml;
  };

  /**
   * Gets the categories from the tea shop and formats and places them on the site.
   */
  const setCategoryButton = function () {
    const filterSection = document.querySelector(".products__box--search");
    filterSection.insertAdjacentHTML(
      "beforeend",
      `<button class="btn btn--text filter" id="All">All products</button>`
    );

    getJSON(`${URL}${PRODUCT_CATEGORY_API_PATHNAME}`).then((response) => {
      response.forEach((category) => {
        filterSection.insertAdjacentHTML(
          "beforeend",
          createCategoryButton(category)
        );
      });

      const categoryButtons = document.querySelectorAll(".filter");
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
    return `<button class="btn btn--text filter" data-categoryid="${category.id}" id="${category.name}">${category.name}</button>`;
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
  }

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
      getJSON(`${URL}${PRODUCT_API_PATHNAME}`).then((response) => {

        response.forEach((product) => {
          const productInfoAsList = Object.values(product);

          productInfoAsList.forEach((productInfo) => {
            if (productInfo !== null && productInfo !== undefined) {
              productInfo = productInfo.toString();

              if (productInfo.toLowerCase().includes(searchString) && !productsFound.includes(product)) {
                productsFound.push(product);
              }
            }
          });
        });
        emptyProductContainer();
        setProductsToProductCards(productsFound);
      });
    }

    /**
     * Searches after the products which matches the search string from the event.
     * A search will happen for every 3rd letter in the input.
     *
     * @param event event form search input.
     */
    const automaticSearchWhenTyping = function (event) {
      event.preventDefault();
      const searchString = event.target.value.toLowerCase();

      // If empty show all product.
      if (searchString === "") {
        emptyProductContainer();
        allProductsButton.click();

        // Wait until user has written 3 letters, so we don't cause too many epileptic seizures.
      } else if (searchString.length % 3 === 0) {
        sendSearchRequest(searchString);
      }
    };

    /**
     * Searches when triggered, takes input from search bar.
     */
    const manualSearch = function (event) {
      event.preventDefault();
      const searchString = searchBar.value;
      sendSearchRequest(searchString)
    }

    searchForm.addEventListener("submit", event => event.preventDefault());
    searchBar.addEventListener("input", automaticSearchWhenTyping);
    searchButton.addEventListener("click", manualSearch);
  };
  setSearchBar();
  setCategoryButton();

  const allProductsButton = document.querySelector("#All");
  allProductsButton.addEventListener("click", getAndSetAllProducts);
  allProductsButton.click();
};

setProductsPage();
