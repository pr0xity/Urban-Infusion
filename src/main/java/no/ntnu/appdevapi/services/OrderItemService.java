package no.ntnu.appdevapi.services;

import java.util.List;
import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.OrderItem;

/**
 * Business logic for order items.
 */
public interface OrderItemService {

  /**
   * Returns the order item with the given id.
   *
   * @param id id of the order item to find.
   * @return order item with the given id, null if not found.
   */
  OrderItem getOrderItem(long id);

  /**
   * Returns all order items.
   *
   * @return all order items.
   */
  List<OrderItem> getAllOrderItems();

  /**
   * Returns list of order items with the given order details.
   *
   * @param orderDetails the order detail to find order items from.
   * @return list of order items associated with the given order details.
   */
  List<OrderItem> getOrderItemsByOrderDetails(OrderDetails orderDetails);

  /**
   * Adds the given order item.
   *
   * @param orderItem order item to add.
   */
  void addOrderItem(OrderItem orderItem);

  /**
   * Adds an iterable collection of order items;
   *
   * @param orderItems iterable collection of order items to be added.
   */
  void addAllOrderItems(Iterable<OrderItem> orderItems);

  /**
   * Updates the order item with the given id with the order item provided.
   *
   * @param id        id of the order item to update.
   * @param orderItem the order item to update to.
   */
  void update(long id, OrderItem orderItem);

  /**
   * Deletes the order item with the given id.
   *
   * @param id id of the order item to delete.
   */
  void deleteOrderItem(long id);

  /**
   * Returns a list of the product id's of the 3 top-selling products.
   *
   * @return list of product id's of 3 top-selling products.
   */
  List<Long> getIdOfTop3SellingProducts();
}
