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

    @Override
    public CartItem getCartItem(long id) {
        return this.cartItemRepository.findById(id).orElse(null);
    }

    @Override
    public CartItem getCartItemByShoppingSessionAndProduct(ShoppingSession shoppingSession, Product product) {
        return this.cartItemRepository.findCartItemByShoppingSessionAndProduct(shoppingSession, product).orElse(null);
    }

    @Override
    public void addCartItem(CartItem cartItem) {
        this.cartItemRepository.save(cartItem);
    }

    @Override
    public void update(long id, CartItem cartItem) {
        this.cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(long id) {
        this.cartItemRepository.deleteById(id);
    }
}
