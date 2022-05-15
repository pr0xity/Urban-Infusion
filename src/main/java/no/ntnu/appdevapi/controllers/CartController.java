package no.ntnu.appdevapi.controllers;

import no.ntnu.appdevapi.entities.*;
import no.ntnu.appdevapi.services.CartItemService;
import no.ntnu.appdevapi.services.ProductService;
import no.ntnu.appdevapi.services.ShoppingSessionService;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API controller for cart/shopping sessions.
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingSessionService shoppingSessionService;

    /**
     * Get all shopping sessions.
     *
     * @return list of all shopping sessions.
     */
    @GetMapping
    public List<ShoppingSession> getAll() {
        return this.shoppingSessionService.getAllShoppingSessions();
    }

    /**
     * Adds or increases quantity of the cart item with the given product id
     * for the current user.
     *
     * @param productId id of the product in the cart item to be added.
     * @return 200 OK if added/increased quantity, 400 Bad request if an issue occurred.
     */
    @RequestMapping(value = "/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<?> addProductToCart(@PathVariable long productId) {
        CartItem cartItem = getCartItem(productId);

        if (!canCartItemBeModified(productId, cartItem) && getProduct(productId) != null) {
            CartItem newCartItem = new CartItem(getShoppingSession(), getProduct(productId));
            cartItemService.addCartItem(newCartItem);
            updateShoppingSession();

            return new ResponseEntity<>(HttpStatus.OK);
        } else if (canCartItemBeModified(productId, cartItem)) {
            cartItem.increaseQuantity();
            cartItemService.update(cartItem.getId(), cartItem);
            updateShoppingSession();

            return new ResponseEntity<>(HttpStatus.OK);
        } else if (getUser() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes or decreases quantity of the cart item with the given product id
     * for this user.
     *
     * @param productId id of the product to be deleted.
     * @return 200 OK if deleted/decreased, 400 Bad request if an issue occurred.
     */
    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProductFromCart(@PathVariable long productId) {
        CartItem cartItem = getCartItem(productId);

        if (canCartItemBeModified(productId, cartItem) && cartItem.getQuantity() > 1) {
            cartItem.decreaseQuantity();
            cartItemService.update(cartItem.getId(), cartItem);
            updateShoppingSession();

            return new ResponseEntity<>(HttpStatus.OK);
        } else if (canCartItemBeModified(productId, cartItem) && cartItem.getQuantity() == 1) {
            cartItemService.deleteCartItem(cartItem.getId());
            updateShoppingSession();

            return new ResponseEntity<>(HttpStatus.OK);
        } else if (getUser() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
     * Returns the product with the given id, null of no product found.
     *
     * @param id id of the product to be found.
     * @return the product with the given id, or null.
     */
    private Product getProduct(long id) {
        return this.productService.getProduct(id);
    }

    /**
     * Returns the shopping session for the current user.
     *
     * @return shopping session for the current user.
     */
    private ShoppingSession getShoppingSession() {
        return this.shoppingSessionService.getShoppingSessionByUser(getUser());
    }

    /**
     * Updates total and quantity fields in the current shopping session.
     */
    private void updateShoppingSession() {
        getShoppingSession().updateQuantity();
        getShoppingSession().updateTotal();
        shoppingSessionService.update(getShoppingSession().getId(), getShoppingSession());
    }

    /**
     * Returns the cart item with the given shopping session and product, null if no cart item
     * exists.
     *
     * @param id id of the product the cart item contains.
     * @return cart item with the given shopping session and product, null if not found.
     */
    private CartItem getCartItem(long id) {
        return this.cartItemService.getCartItemByShoppingSessionAndProduct(getShoppingSession(), getProduct(id));
    }

    /**
     * Returns true of conditions to edit an existing cart item are met.
     *
     * @param productId product id of the cart item.
     * @param cartItem cart item to check
     * @return true if cart item exists and can be modified, null if not.
     */
    private boolean canCartItemBeModified(long productId, CartItem cartItem) {
        return getProduct(productId) != null && getUser() != null && cartItem != null;
    }
}
