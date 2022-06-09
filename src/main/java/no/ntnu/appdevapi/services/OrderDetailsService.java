package no.ntnu.appdevapi.services;

import java.util.List;
import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.User;

/**
 * Business logic for order items.
 */
public interface OrderDetailsService {
  /**
   * Returns a list over all order details.
   *
   * @return list over all order details.
   */
  List<OrderDetails> getAllOrderDetails();

  /**
   * Returns a list of the (up to) five most recent orders.
   *
   * @return list of the five most recent orders.
   */
  List<OrderDetails> getRecentOrderDetails();

  /**
   * Returns the order details with the given id.
   *
   * @param id id of the order details to find.
   * @return the order details with the given id or null if not found.
   */
  OrderDetails getOrderDetails(long id);

  /**
   * Returns the order details of the given user.
   *
   * @param user the user to find the order details of.
   * @return the order details of the given user or null if not found.
   */
  List<OrderDetails> getOrderDetailsByUser(User user);

  /**
   * Adds a order details.
   *
   * @param orderDetails order details to be added.
   */
  void addOrderDetails(OrderDetails orderDetails);

  /**
   * Updates the order details with the given id.
   *
   * @param id           the id of the order details to update.
   * @param orderDetails the order details to update to.
   */
  void update(long id, OrderDetails orderDetails);

  /**
   * Deletes the order details with the given id.
   *
   * @param id id of the order details to be deleted.
   */
  void deleteOrderDetails(long id);
}
