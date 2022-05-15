package no.ntnu.appdevapi.controllers;

import no.ntnu.appdevapi.entities.*;
import no.ntnu.appdevapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * MVC controller for the html pages.
 */
@Controller
public class HTMLPageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ShoppingSessionService shoppingSessionService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrderItemService orderItemService;

    /**
     * Gets the home page with the required attributes. Returns index thymeleaf template.
     *
     * @param model model for homepage.
     * @return index thymeleaf template.
     */
    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("user", this.getUser());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("topSellingProducts", getTop3SellingProducts());
        Wishlist wishlist = this.wishlistService.getWishlistByUser(this.getUser());
        if (wishlist != null) {
            model.addAttribute("wishlist", wishlist.getProducts());
        }

        this.addPermissionLevelToModel(model);

        return "index";
    }

    /**
     * Displays this users account information.
     *
     * @param model model for account.
     * @return account thymeleaf template.
     */
    @GetMapping("/account")
    public String getAccount(Model model) {

        model.addAttribute("user" , this.getUser());
        model.addAttribute("address", userAddressService.getUserAddressByUserID(userService.getSessionUser().getId()).getAddressLine());
        model.addAttribute("orderDetails", orderDetailsService.getOrderDetailsByUser(getUser()));

        this.addPermissionLevelToModel(model);
        return "account";
    }

    /**
     * Gets and displays the specified product.
     *
     * @param id the id of the product to get.
     * @param model model for product.
     * @return product thymeleaf template.
     */
    @GetMapping("product/{id}")
    public String getProduct(@PathVariable long id, Model model) {
        Product product = productService.getProduct(id);
        Wishlist wishlist = this.wishlistService.getWishlistByUser(this.getUser());

        if (wishlist != null){
            model.addAttribute("wishlist", wishlist.getProducts());
        }

        model.addAttribute("product", product);
        model.addAttribute("user", this.getUser());
        model.addAttribute("description", product.getDescription().split("\\."));
        model.addAttribute("comments", ratingService.getRatingsFromProduct(product));
        this.addPermissionLevelToModel(model);
        return "product";
    }

    /**
     * Displays the current users wishlist.
     *
     * @return wishlist thymeleaf template.
     */
    @GetMapping("wishlist")
    public String getWishlist(Model model) {
        model.addAttribute("user", this.getUser());
        Wishlist wishlist = this.wishlistService.getWishlistByUser(this.getUser());

        if (wishlist != null){
            model.addAttribute("wishlist", wishlist.getProducts());
        }

        this.addPermissionLevelToModel(model);

        return "wishlist";
    }

    /**
     * Displays the current users shopping session.
     *
     * @return checkout thymeleaf template.
     */
    @GetMapping("checkout")
    public String getCheckout(Model model) {
        UserAddress userAddress = userAddressService.getUserAddressByUserID(this.getUser().getId());

        model.addAttribute("user", this.getUser());
        model.addAttribute("address", userAddress.getAddressLine1() + ", " + userAddress.getPostalCode() + " " + userAddress.getCity());
        ShoppingSession shoppingSession = this.shoppingSessionService.getShoppingSessionByUser(this.getUser());

        if (shoppingSession != null){
            model.addAttribute("cart", shoppingSession.getCart());
        }

        this.addPermissionLevelToModel(model);

        return "checkout";
    }

    /**
     * Displays the current users shopping session.
     *
     * @return checkout thymeleaf template.
     */
    @GetMapping("signup")
    public String getSignup(Model model) {
        this.addPermissionLevelToModel(model);
        return "signup";
    }

    /**
     * Retrieves permission level and adds to the given model.
     *
     * @param model model to add permission level to.
     */
    private void addPermissionLevelToModel(Model model) {
        if(this.getUser() != null) {
            model.addAttribute("permission", this.getUser().getPermissionLevel().getAdminType());
        } else {
            model.addAttribute("permission", "NoUser");
        }
    }

    /**
     * Returns the user of this session.
     *
     * @return user of this session.
     */
    private User getUser() {
        return this.userService.getSessionUser();
    }

    /**
     * Returns a list over the 3 top-selling products.
     *
     * @return list over 3 top-selling products.
     */
    private List<Product> getTop3SellingProducts() {
        List<Product> topSellingProducts = new ArrayList<>();

        List<Long> productIds = orderItemService.getIdOfTop3SellingProducts();
        if (productIds.isEmpty() || productIds.size() < 3) {
            //Add some arbitrary products if no/less than 3 best sellers.
            productService.getAllProducts().forEach(topSellingProducts::add);
            topSellingProducts = topSellingProducts.subList(0, 3);

            if (!productIds.isEmpty()) {
                int index = 1;
                for (Long id : productIds) {
                    //Swap if top sellers not in the first few products.
                    if (!topSellingProducts.contains(productService.getProduct(id))) {
                        topSellingProducts.set(index, productService.getProduct(id));
                        index++;
                    }
                }
            }
        } else {
            for (Long id : productIds) {
                topSellingProducts.add(productService.getProduct(id));
            }
        }

        return topSellingProducts;
    }
}
