package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.CartItem;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ShoppingSession;

/**
 * Business logic for cart items.
 */
public interface CartItemService {

    CartItem getCartItem(long id);

    CartItem getCartItemByShoppingSessionAndProduct(ShoppingSession shoppingSession, Product product);

    void addCartItem(CartItem cartItem);

    void update(long id, CartItem cartItem);

    void deleteCartItem(long id);
}
