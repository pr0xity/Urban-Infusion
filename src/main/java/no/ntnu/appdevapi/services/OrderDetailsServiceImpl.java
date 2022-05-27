package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.OrderDetailsRepository;
import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for order items.
 */
@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
    
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<OrderDetails> getAllOrderDetails() {
        List<OrderDetails> OrderDetailss = new ArrayList<>();
        orderDetailsRepository.findAll().forEach(OrderDetailss::add);
        return OrderDetailss;
    }

    @Override
    public OrderDetails getOrderDetails(long id) {
        Optional<OrderDetails> OrderDetails = orderDetailsRepository.findById(id);
        return OrderDetails.orElse(null);
    }

    @Override
    public List<OrderDetails> getOrderDetailsByUser(User user) {
        return this.orderDetailsRepository.findAllByUser(user);
    }

    @Override
    public void addOrderDetails(OrderDetails OrderDetails) {
        orderDetailsRepository.save(OrderDetails);
    }

    @Override
    public void update(long id, OrderDetails OrderDetails) {
        if (OrderDetails != null && OrderDetails.getId() == id && getOrderDetails(id) != null) {
            this.orderDetailsRepository.save(OrderDetails);
        }
    }

    @Override
    public void deleteOrderDetails(long id) {
        orderDetailsRepository.deleteById(id);
    }
}
