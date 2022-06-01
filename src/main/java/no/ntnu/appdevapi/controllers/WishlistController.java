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
import org.springframework.web.bind.annotation.*;

/**
 * REST API controller for wishlists.
 */
@RestController
@RequestMapping("api/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getWishlist() {
        User user = userService.getSessionUser();
        if (user != null) {
            Wishlist wishlist = wishlistService.getWishlistByUser(user);
            return new ResponseEntity<>(wishlist, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Adds the product with the given id to the current user's wishlist.
     *
     * @param id id of the product to be added.
     * @return 200 OK if added, 400 Bad request if an issue occurred.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> addProductToWishlist(@PathVariable long id) {
        Product product = productService.getProduct(id);
        User user = userService.getSessionUser();
        Wishlist wishlist = wishlistService.getWishlistByUser(user);
        if (product != null && user != null && !wishlist.getProducts().contains(product)) {
            wishlistService.addProductToWishlist(wishlist, product);
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
        } else if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
