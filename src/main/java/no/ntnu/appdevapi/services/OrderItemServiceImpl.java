package no.ntnu.appdevapi.services;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.appdevapi.DAO.OrderItemRepository;
import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic for order items.
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

  @Autowired
  private OrderItemRepository orderItemRepository;

  /**
   * Gets the order item with the given ID.
   *
   * @param id id of the order item to find.
   * @return {@code OrderItem} with given ID.
   */
  @Override
  public OrderItem getOrderItem(long id) {
    return this.orderItemRepository.findById(id).orElse(null);
  }

  /**
   * Gets a list of all order items.
   *
   * @return {@code List<OrderItem>} of all order items in the database.
   */
  @Override
  public List<OrderItem> getAllOrderItems() {
    List<OrderItem> orderItems = new ArrayList<>();
    orderItemRepository.findAll().forEach(orderItems::add);
    return orderItems;
  }

  /**
   * Gets a list of all order items in the given order.
   *
   * @param orderDetails the order to find order items from.
   * @return {@code List<OrderItem>} of all order items in given order.
   */
  @Override
  public List<OrderItem> getOrderItemsByOrderDetails(OrderDetails orderDetails) {
    return new ArrayList<>(orderItemRepository.findAllByOrderDetails(orderDetails));
  }

  /**
   * Adds an order item to the database.
   *
   * @param orderItem {@code OrderItem} to be added.
   */
  @Override
  public void addOrderItem(OrderItem orderItem) {
    this.orderItemRepository.save(orderItem);
  }

  /**
   * Adds a collection of order items to the database.
   *
   * @param orderItems iterable collection of order items to be added.
   */
  @Override
  public void addAllOrderItems(Iterable<OrderItem> orderItems) {
    this.orderItemRepository.saveAll(orderItems);
  }

  /**
   * Updates the order item with the given ID.
   *
   * @param id        ID of the order item to update.
   * @param orderItem the {@code OrderItem} to update to.
   */
  @Override
  public void update(long id, OrderItem orderItem) {
    if (orderItem != null && orderItem.getId() == id && getOrderItem(id) != null) {
      this.orderItemRepository.save(orderItem);
    }
  }

  /**
   * Deletes the order item with the given ID.
   *
   * @param id ID of the order item to delete.
   */
  @Override
  public void deleteOrderItem(long id) {
    this.orderItemRepository.deleteById(id);
  }

  /**
   * Gets a list of the IDs of the three most sold products.
   *
   * @return {@code List<Long>} of the three most sold products.
   */
  @Override
  public List<Long> getIdOfTop3SellingProducts() {
    List<Long> topSellingProducts = new ArrayList<>();

    if (!orderItemRepository.findTopBestSellingProducts().isEmpty()) {
      topSellingProducts.addAll(orderItemRepository.findTopBestSellingProducts());
    }

    return topSellingProducts;
  }
}