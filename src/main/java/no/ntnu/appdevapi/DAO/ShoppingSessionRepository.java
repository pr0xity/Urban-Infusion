package no.ntnu.appdevapi.DAO;

import java.util.Optional;
import no.ntnu.appdevapi.entities.ShoppingSession;
import no.ntnu.appdevapi.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for shopping sessions.
 */
@Repository
public interface ShoppingSessionRepository extends CrudRepository<ShoppingSession, Long> {
  Optional<ShoppingSession> findByUser(User user);
}
