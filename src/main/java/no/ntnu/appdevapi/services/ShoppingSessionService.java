package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.ShoppingSession;
import no.ntnu.appdevapi.DAO.ShoppingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for shopping sessions.
 */
@Service
public class ShoppingSessionService {

    @Autowired
    private ShoppingSessionRepository shoppingSessionRepository;

    /**
     * Returns a list over all shopping sessions.
     *
     * @return list over all shopping sessions.
     */
    public List<ShoppingSession> getAllShoppingSessions() {
        List<ShoppingSession> shoppingSessions = new ArrayList<>();
        shoppingSessionRepository.findAll().forEach(shoppingSessions::add);
        return shoppingSessions;
    }

    /**
     * Returns the shopping sessions with the given id.
     *
     * @param id id of the shopping session to find.
     * @return the shopping session with the given id or null if not found.
     */
    public ShoppingSession getShoppingSession(int id) {
        Optional<ShoppingSession> shoppingSession = shoppingSessionRepository.findById(id);
        return shoppingSession.orElse(null);
    }

    /**
     * Adds a shopping session.
     *
     * @param shoppingSession shopping sessions to be added.
     */
    public void addShoppingSession(ShoppingSession shoppingSession) {
        shoppingSessionRepository.save(shoppingSession);
    }

    /**
     * Updates the shopping sessions with the given id.
     *
     * @param id the id of the shopping session to update.
     * @param shoppingSession the shopping session to update to.
     */
    public void update(int id, ShoppingSession shoppingSession) {
        if (shoppingSession != null && shoppingSession.getId() == id && getShoppingSession(id) != null) {
            this.shoppingSessionRepository.save(shoppingSession);
        }
    }

    /**
     * Deletes the shopping session with the given id.
     *
     * @param id id of the shopping session to be deleted.
     */
    public void deleteShoppingSession(int id) {
        shoppingSessionRepository.deleteById(id);
    }
}
