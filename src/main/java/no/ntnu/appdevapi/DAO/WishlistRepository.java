package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.Wishlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for wishlists.
 */
@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Long> {
    Optional<Wishlist> findByUser(User user);

    Optional<Wishlist> findBySharingToken(String sharingToken);
}
