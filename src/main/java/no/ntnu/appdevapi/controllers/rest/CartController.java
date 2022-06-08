package no.ntnu.appdevapi.controllers.rest;

import no.ntnu.appdevapi.DTO.CartItemDto;
import no.ntnu.appdevapi.entities.*;
import no.ntnu.appdevapi.services.CartItemService;
import no.ntnu.appdevapi.services.ProductService;
import no.ntnu.appdevapi.services.ShoppingSessionService;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST API controller for cart/shopping sessions.
 */
@CrossOrigin
@RestController
@RequestMapping("api/carts")
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
     * Returns the current user's shopping session.
     *
     * @return current user's shopping session.
     */
    @GetMapping()
    public ResponseEntity<ShoppingSession> get() {
        ShoppingSession shoppingSession = this.getShoppingSession();

        if (shoppingSession != null) {
            return new ResponseEntity<>(shoppingSession, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Adds or increases quantity of the cart item with the given product id
     * for the current user.
     *
     * @param productId id of the product in the cart item to be added.
     * @return 200 OK if added/increased quantity, 400 Bad request if an issue occurred.
     */
    @RequestMapping(value = "/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<?> addOrUpdateProductToCart(@PathVariable long productId, @RequestBody Optional<CartItemDto> cartItemDto) {
        CartItem cartItem = getCartItem(productId);

        if (getUser() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else if (!canCartItemBeModified(productId, cartItem) && getProduct(productId) != null) {
            CartItem newCartItem = new CartItem(getShoppingSession(), getProduct(productId));
            cartItemService.addCartItem(newCartItem);
            updateShoppingSession();

            return new ResponseEntity<>(HttpStatus.OK);
        } else if (canCartItemBeModified(productId, cartItem)) {
            if (cartItemDto.isPresent()) {
                cartItem.setQuantity(cartItemDto.get().getQuantity());
                cartItemService.update(cartItem.getId(), cartItem);
                updateShoppingSession();
                return new ResponseEntity<>(HttpStatus.OK);
            }
            cartItem.increaseQuantity();
            cartItemService.update(cartItem.getId(), cartItem);
            updateShoppingSession();

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes the cart item with the given product id for this user.
     *
     * @param productId id of the product to be deleted.
     * @return 200 OK if deleted, 400 Bad request if an issue occurred.
     */
    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProductFromCart(@PathVariable long productId) {
        CartItem cartItem = getCartItem(productId);

        if (canCartItemBeModified(productId, cartItem)) {
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
