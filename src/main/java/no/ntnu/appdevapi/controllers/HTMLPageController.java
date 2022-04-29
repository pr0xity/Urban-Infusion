package no.ntnu.appdevapi.controllers;

import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.Wishlist;
import no.ntnu.appdevapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

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
    private UserServiceImpl userService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private WishlistService wishlistService;

    /**
     * Gets the home page with the required attributes. Returns index thymeleaf template.
     *
     * @param model model for homepage.
     * @return index thymeleaf template.
     */
    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("user", userService.getSessionUser());
        model.addAttribute("products", productService.getAllProducts());

        if(userService.getSessionUser() != null) {
            model.addAttribute("permission", userService.getSessionUser().getPermissionLevel().getAdminType());
        } else {
            model.addAttribute("permission", "NoUser");
        }

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

        model.addAttribute("user" , userService.getSessionUser());
        model.addAttribute("address", userAddressService.getUserAddressByUserID(userService.getSessionUser().getId()).getAddressLine());
        if(userService.getSessionUser() != null) {
            model.addAttribute("permission", userService.getSessionUser().getPermissionLevel().getAdminType());
        } else {
            model.addAttribute("permission", "NoUser");
        }
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
    public String getProduct(@PathVariable int id, Model model) {
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        model.addAttribute("user", userService.getSessionUser());
        model.addAttribute("description", product.getDescription().split("\\."));
        model.addAttribute("comments", ratingService.getRatingsFromProduct(product));
        if(userService.getSessionUser() != null) {
            model.addAttribute("permission", userService.getSessionUser().getPermissionLevel().getAdminType());
        } else {
            model.addAttribute("permission", "NoUser");
        }
        return "product";
    }

    /**
     *
     */
    @GetMapping("wishlist")
    public String getWishlist(Model model) {
        model.addAttribute("user", userService.getSessionUser());
        Wishlist wishlist = this.wishlistService.getWishlistByUser(this.userService.getSessionUser());
        if (wishlist != null){
            model.addAttribute("wishlist", this.wishlistService.getWishlistByUser(this.userService.getSessionUser()).getProducts());
        }
        if(userService.getSessionUser() != null) {
            model.addAttribute("permission", userService.getSessionUser().getPermissionLevel().getAdminType());
        } else {
            model.addAttribute("permission", "NoUser");
        }
        return "wishlist";
    }

}
