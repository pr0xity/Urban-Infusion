package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.CartItemRepository;
import no.ntnu.appdevapi.entities.CartItem;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ShoppingSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic for cart items.
 */
@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    private CartItemRepository cartItemRepository;

    /**
     * Gets the cart item with the given ID.
     *
     * @param id id of the cart item to find.
     * @return {@Code CartItem} with given ID, or {@code null} if none found.
     */
    @Override
    public CartItem getCartItem(long id) {
        return this.cartItemRepository.findById(id).orElse(null);
    }

    /**
     * Gets the cart item by given shopping session and product.
     *
     * @param shoppingSession the shopping session to find cart item for.
     * @param product the product the cart item contains.
     * @return {@code CartItem} found, or {@code null} if none found.
     */
    @Override
    public CartItem getCartItemByShoppingSessionAndProduct(ShoppingSession shoppingSession, Product product) {
        return this.cartItemRepository.findCartItemByShoppingSessionAndProduct(shoppingSession, product).orElse(null);
    }

    /**
     * Adds a cart item to the database.
     *
     * @param cartItem cart item to be added.
     */
    @Override
    public void addCartItem(CartItem cartItem) {
        this.cartItemRepository.save(cartItem);
    }

    /**
     * Updates a cart item with given in the database.
     *
     * @param id ID of the cart item to update.
     * @param cartItem the updated {@code CartItem}.
     */
    @Override
    public void update(long id, CartItem cartItem) {
        this.cartItemRepository.save(cartItem);
    }

    /**
     * Removes a cart item from the database.
     *
     * @param id id of the cart item to delete.
     */
    @Override
    public void deleteCartItem(long id) {
        this.cartItemRepository.deleteById(id);
    }

    /**
     * Deletes all cart item in the given shopping session.
     *
     * @param shoppingSession shopping session to delete cart items from.
     */
    @Override
    public void deleteAllCartItemsInShoppingSession(ShoppingSession shoppingSession) {
        this.cartItemRepository.deleteAllByShoppingSession(shoppingSession);
    }
}
