package no.ntnu.appdevapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import no.ntnu.appdevapi.DAO.ShoppingSessionRepository;
import no.ntnu.appdevapi.entities.ShoppingSession;
import no.ntnu.appdevapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic for shopping sessions.
 */
@Service
public class ShoppingSessionServiceImpl implements ShoppingSessionService {

  @Autowired
  private ShoppingSessionRepository shoppingSessionRepository;

  /**
   * Gets a list of all shopping sessions.
   *
   * @return {@code List<ShoppingSession>} of all shopping sessions.
   */
  @Override
  public List<ShoppingSession> getAllShoppingSessions() {
    List<ShoppingSession> shoppingSessions = new ArrayList<>();
    shoppingSessionRepository.findAll().forEach(shoppingSessions::add);
    return shoppingSessions;
  }

  /**
   * Gets the shopping session with the given ID.
   *
   * @param id id of the shopping session to find.
   * @return {@code ShoppingSession} matching the given ID, or {@code null} if no match found.
   */
  @Override
  public ShoppingSession getShoppingSession(long id) {
    Optional<ShoppingSession> shoppingSession = shoppingSessionRepository.findById(id);
    return shoppingSession.orElse(null);
  }

  /**
   * Gets the shopping session belonging to the given user.
   *
   * @param user the user to find the shopping session of.
   * @return {@code ShoppingSession} belonging to the given user.
   */
  @Override
  public ShoppingSession getShoppingSessionByUser(User user) {
    return this.shoppingSessionRepository.findByUser(user).orElse(null);
  }

  /**
   * Adds a shopping session to the database.
   *
   * @param shoppingSession shopping sessions to be added.
   */
  @Override
  public void addShoppingSession(ShoppingSession shoppingSession) {
    shoppingSessionRepository.save(shoppingSession);
  }

  /**
   * Updates the shopping session with the given ID.
   *
   * @param id              the ID of the shopping session to update.
   * @param shoppingSession the shopping session to update to.
   */
  @Override
  public void update(long id, ShoppingSession shoppingSession) {
    if (shoppingSession != null && shoppingSession.getId() == id &&
            getShoppingSession(id) != null) {
      this.shoppingSessionRepository.save(shoppingSession);
    }
  }

  /**
   * Deletes the shopping session with the given ID from the database.
   *
   * @param id ID of the shopping session to be deleted.
   */
  @Override
  public void deleteShoppingSession(long id) {
    shoppingSessionRepository.deleteById(id);
  }
}
