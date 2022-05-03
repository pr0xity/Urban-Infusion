package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.ShoppingSession;
import no.ntnu.appdevapi.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository interface for shopping sessions.
 */
public interface ShoppingSessionRepository extends CrudRepository<ShoppingSession, Long> {
    Optional<ShoppingSession> findByUser(User user);
}
