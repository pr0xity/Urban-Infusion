package no.ntnu.appdevapi.events;

import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.OrderItem;
import no.ntnu.appdevapi.entities.User;
import org.springframework.context.ApplicationEvent;

/**
 * Event for completing order.
 */
public class CompleteOrderEvent extends ApplicationEvent {

  /**
   * The user who ordered.
   */
  private final User user;

  /**
   * The details of the order.
   */
  private final OrderDetails orderDetails;

  /**
   * The items ordered.
   */
  private final Iterable<OrderItem> orderItems;

  /**
   * Creates an instance of complete order event.
   *
   * @param user         the user of this complete order event.
   * @param orderDetails the order details of this complete order event.
   * @param orderItems   the order items of this complete order event.
   */
  public CompleteOrderEvent(User user, OrderDetails orderDetails, Iterable<OrderItem> orderItems) {
    super(user);
    this.user = user;
    this.orderDetails = orderDetails;
    this.orderItems = orderItems;
  }

  /**
   * Returns the user of this complete order event.
   *
   * @return user of this complete order event.
   */
  public User getUser() {
    return user;
  }

  /**
   * Returns the order details of this complete order event.
   *
   * @return the order details of this complete order event.
   */
  public OrderDetails getOrderDetails() {
    return orderDetails;
  }

  /**
   * Returns the order items of this complete order event.
   *
   * @return the order items of this complete order event.
   */
  public Iterable<OrderItem> getOrderItems() {
    return orderItems;
  }
}
