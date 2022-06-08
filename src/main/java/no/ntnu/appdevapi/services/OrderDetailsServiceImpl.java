package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.OrderDetailsRepository;
import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Business logic for order items.
 */
@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
    
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    /**
     * Gets all orders in the database
     * sorted first by processed status (unprocessed first),
     * then by creation date (the newest first).
     *
     * @return sorted {@code List<OrderDetails>} of all orders in the database.
     */
    @Override
    public List<OrderDetails> getAllOrderDetails() {
        List<OrderDetails> orderDetails = new ArrayList<>();
        orderDetailsRepository.findAll().forEach(orderDetails::add);
        return orderDetails.stream()
                .sorted(Comparator
                        .comparing(OrderDetails::isProcessed)
                        .thenComparing(OrderDetails::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Gets the (up to) five most recent orders, sorted by creation date (the newest first).
     *
     * @return sorted {@code List<OrderDetails>} of the (up to) five most recent orders.
     */
    @Override
    public List<OrderDetails> getRecentOrderDetails() {
        List<OrderDetails> orderDetails = new ArrayList<>();
        orderDetailsRepository.findAll().forEach(orderDetails::add);
        int k = orderDetails.size();
        if (k > 5) {
            orderDetails.subList(5,k).clear();
        }
        return orderDetails.stream()
                .sorted(Comparator
                        .comparing(OrderDetails::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Gets the order with the given ID.
     *
     * @param id ID of the order details to find.
     * @return {@code OrderDetails} with given ID.
     */
    @Override
    public OrderDetails getOrderDetails(long id) {
        Optional<OrderDetails> OrderDetails = orderDetailsRepository.findById(id);
        return OrderDetails.orElse(null);
    }

    /**
     * Gets a list of all orders made by given user.
     *
     * @param user the {@code User} to find the order details of.
     * @return {@code List<OrderDetails>} of orders made by given user.
     */
    @Override
    public List<OrderDetails> getOrderDetailsByUser(User user) {
        return this.orderDetailsRepository.findAllByUser(user);
    }

    /**
     * Adds the given order details to the database.
     *
     * @param OrderDetails {@code OrderDetails} to be added.
     */
    @Override
    public void addOrderDetails(OrderDetails OrderDetails) {
        orderDetailsRepository.save(OrderDetails);
    }

    /**
     * Updates the order with the given ID.
     * @param id the ID of the order details to update.
     * @param OrderDetails the updated {@code OrderDetails}.
     */
    @Override
    public void update(long id, OrderDetails OrderDetails) {
        if (OrderDetails != null && OrderDetails.getId() == id && getOrderDetails(id) != null) {
            this.orderDetailsRepository.save(OrderDetails);
        }
    }

    /**
     * Deletes the order details with the given id from the database.
     *
     * @param id ID of the order details to be deleted.
     */
    @Override
    public void deleteOrderDetails(long id) {
        orderDetailsRepository.deleteById(id);
    }
}
