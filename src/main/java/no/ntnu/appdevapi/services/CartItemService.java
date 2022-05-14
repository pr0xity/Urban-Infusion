package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.CartItem;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ShoppingSession;

/**
 * Business logic for cart items.
 */
public interface CartItemService {

    /**
     * Returns the cart item with the given id.
     *
     * @param id id of the cart item to find.
     * @return cart item with the given id, null if not found.
     */
    CartItem getCartItem(long id);

    /**
     * Returns the cart item belonging to the given shopping session
     * and containing the given product.
     *
     * @param shoppingSession the shopping session to find cart item for.
     * @param product the product the cart item contains.
     * @return the cart item with the given shopping session and product, null if not found.
     */
    CartItem getCartItemByShoppingSessionAndProduct(ShoppingSession shoppingSession, Product product);

    /**
     * Adds the given cart item.
     *
     * @param cartItem cart item to add.
     */
    void addCartItem(CartItem cartItem);

    /**
     * Updates the cart item with the given id with the cart item provided.
     *
     * @param id id of the cart item to update.
     * @param cartItem the cart item to update to.
     */
    void update(long id, CartItem cartItem);

    /**
     * Deletes the cart item with the given id.
     *
     * @param id id of the cart item to delete.
     */
    void deleteCartItem(long id);

    /**
     * Deletes all the cart items in the given shopping sessions.
     *
     * @param shoppingSession shopping session to delete cart items from.
     */
    void deleteAllCartItemsInShoppingSession(ShoppingSession shoppingSession);
}
