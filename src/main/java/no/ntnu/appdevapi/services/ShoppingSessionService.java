package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.ShoppingSession;
import no.ntnu.appdevapi.entities.User;

import java.util.List;

/**
 * Business logic for shopping sessions.
 */
public interface ShoppingSessionService {

  /**
   * Returns a list over all shopping sessions.
   *
   * @return list over all shopping sessions.
   */
  List<ShoppingSession> getAllShoppingSessions();

  /**
   * Returns the shopping sessions with the given id.
   *
   * @param id id of the shopping session to find.
   * @return the shopping session with the given id or null if not found.
   */
  ShoppingSession getShoppingSession(long id);

  /**
   * Returns the shopping session of the given user.
   *
   * @param user the user to find the shopping session of.
   * @return the shopping session of the given user or null if not found.
   */
  ShoppingSession getShoppingSessionByUser(User user);

  /**
   * Adds a shopping session.
   *
   * @param shoppingSession shopping sessions to be added.
   */
  void addShoppingSession(ShoppingSession shoppingSession);

  /**
   * Updates the shopping sessions with the given id.
   *
   * @param id the id of the shopping session to update.
   * @param shoppingSession the shopping session to update to.
   */
  void update(long id, ShoppingSession shoppingSession);

  /**
   * Deletes the shopping session with the given id.
   *
   * @param id id of the shopping session to be deleted.
   */
  void deleteShoppingSession(long id);
}
