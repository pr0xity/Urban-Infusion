package no.ntnu.appdevapi.shopping_session;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface for shopping sessions.
 */
public interface ShoppingSessionRepository extends CrudRepository<ShoppingSession, Integer> {
}
