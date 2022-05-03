package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.CartItem;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ShoppingSession;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository interface for cart items.
 */
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    Optional<CartItem> findCartItemByShoppingSessionAndProduct(ShoppingSession shoppingSession, Product product);
}
