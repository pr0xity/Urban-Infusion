package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.Wishlist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {
    Optional<Wishlist> findByUser(User user);
}
