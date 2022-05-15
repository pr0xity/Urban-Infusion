package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.OrderItemRepository;
import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic for order items.
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem getOrderItem(long id) {
        return this.orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItemRepository.findAll().forEach(orderItems::add);
        return orderItems;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderDetails(OrderDetails orderDetails) {
        return new ArrayList<>(orderItemRepository.findAllByOrderDetails(orderDetails));
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        this.orderItemRepository.save(orderItem);
    }

    @Override
    public void addAllOrderItems(Iterable<OrderItem> orderItems) {
        this.orderItemRepository.saveAll(orderItems);
    }

    @Override
    public void update(long id, OrderItem orderItem) {
        if (orderItem != null && orderItem.getId() == id && getOrderItem(id) != null) {
            this.orderItemRepository.save(orderItem);
        }
    }

    @Override
    public void deleteOrderItem(long id) {
        this.orderItemRepository.deleteById(id);
    }

    @Override
    public List<Long> getIdOfTop3SellingProducts() {
        List<Long> topSellingProducts = new ArrayList<>();

        if (!orderItemRepository.findTopBestSellingProducts().isEmpty()) {
            topSellingProducts.addAll(orderItemRepository.findTopBestSellingProducts());
        }

        return topSellingProducts;
    }
}
