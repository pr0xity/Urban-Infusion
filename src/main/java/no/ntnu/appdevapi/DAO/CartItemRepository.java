package no.ntnu.appdevapi.DAO;

import java.util.Optional;
import javax.transaction.Transactional;
import no.ntnu.appdevapi.entities.CartItem;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ShoppingSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for cart items.
 */
@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
  Optional<CartItem> findCartItemByShoppingSessionAndProduct(ShoppingSession shoppingSession,
                                                             Product product);

  @Transactional
  void deleteAllByShoppingSession(ShoppingSession shoppingSession);
}
