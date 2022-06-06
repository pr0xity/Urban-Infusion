package no.ntnu.appdevapi.controllers.mvc;

import no.ntnu.appdevapi.entities.*;
import no.ntnu.appdevapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
        model.addAttribute("products", productService.getAllProductsNotDeleted());
        model.addAttribute("topSellingProducts", getTop3SellingProducts());
        Wishlist wishlist = this.wishlistService.getWishlistByUser(this.getUser());
        if (wishlist != null) {
            model.addAttribute("wishlist", wishlist);
        }

        this.addPermissionLevelToModel(model);

        return "index";
    }

    @GetMapping("products")
    public String getAllProducts(Model model) {
        addPermissionLevelToModel(model);
        return "products";
    }

    /**
     * Gets and displays the specified product.
     *
     * @param id the id of the product to get.
     * @param model model for product.
     * @return product thymeleaf template.
     */
    @GetMapping("products/{id}")
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
        model.addAttribute("comment", ratingService.getRatingFromUserAndProduct(getUser(), product));
        this.addPermissionLevelToModel(model);
        return "product";
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
        model.addAttribute("address", this.getUser().getAddress());
        model.addAttribute("orderDetails", orderDetailsService.getOrderDetailsByUser(getUser()));

        this.addPermissionLevelToModel(model);
        return "account";
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
            model.addAttribute("wishlist", wishlist);
        }

        this.addPermissionLevelToModel(model);
        return "wishlist";
    }

    /**
     * Displays the wishlist contents of the wishlist which has the given
     * sharing token.
     *
     * @param sharingToken sharing token to find shared wishlist for.
     * @return wishlist thymeleaf template with the shared wishlist.
     */
    @GetMapping("wishlist/shared/{sharingToken}")
    public String getSharedWishlist(@PathVariable String sharingToken, HttpServletResponse response, Model model) throws IOException {
        model.addAttribute("user", this.getUser());
        Wishlist wishlist = this.wishlistService.getWishlistBySharingToken(sharingToken);

        if (wishlist != null){
            if (wishlist.getUser() == this.getUser()) response.sendRedirect("/wishlist");
            model.addAttribute("wishlist", wishlist);
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
        model.addAttribute("user", this.getUser());
        model.addAttribute("address", this.getUser().getAddress());
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
        List<Product> topSellingProducts = new ArrayList<>(getListOfBestSellingProducts());

        if (topSellingProducts.isEmpty() || topSellingProducts.size() < 3) {
            for (Product product : this.productService.getAllProducts()) {
                if (!topSellingProducts.contains(product)) {
                    topSellingProducts.add(product);
                }
            }

            if (topSellingProducts.size() >= 4) {
                topSellingProducts = topSellingProducts.subList(0, 3);
            }
        }
        return topSellingProducts;
    }

    /**
     * Returns a list of the best-selling products that are not deleted.
     *
     * @return list of the best-selling products that are not deleted.
     */
    private List<Product> getListOfBestSellingProducts() {
        return this.orderItemService.getIdOfTop3SellingProducts().stream()
                .filter(id -> productService.getProduct(id) != null && productService.getProduct(id).getDeletedAt() == null)
                .map(id -> productService.getProduct(id)).toList();
    }
}
