package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.ShoppingSession;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for shopping sessions.
 */
public interface ShoppingSessionRepository extends CrudRepository<ShoppingSession, Integer> {
}
