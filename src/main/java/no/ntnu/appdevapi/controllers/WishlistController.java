package no.ntnu.appdevapi.controllers;

import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.Wishlist;
import no.ntnu.appdevapi.services.ProductService;
import no.ntnu.appdevapi.services.UserService;
import no.ntnu.appdevapi.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * MVC and REST API controller for wishlists.
 */
@Controller
@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    /**
     * Deletes the product with the given id from the current user's wishlist.
     *
     * @param id id of the product to be deleted.
     * @return 200 OK if deleted, 400 Bad request if an issue occurred.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> addProductToWishlist(@PathVariable long id) {
        Product product = productService.getProduct(id);
        User user = userService.getSessionUser();
        if (product != null && user != null) {
            Wishlist wishlist = wishlistService.getWishlistByUser(user);
            wishlistService.addProductToWishlist(wishlist, product);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes the product with the given id from the current user's wishlist.
     *
     * @param id id of the product to be deleted.
     * @return 200 OK if deleted, 400 Bad request if an issue occurred.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProductFromWishlist(@PathVariable long id) {
        Product product = productService.getProduct(id);
        User user = userService.getSessionUser();
        if (product != null && user != null) {
            Wishlist wishlist = wishlistService.getWishlistByUser(user);
            wishlistService.deleteProductFromWishlist(wishlist, product);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
