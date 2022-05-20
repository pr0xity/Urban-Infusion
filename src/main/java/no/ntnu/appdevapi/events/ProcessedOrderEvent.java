package no.ntnu.appdevapi.events;

import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.User;
import org.springframework.context.ApplicationEvent;

/**
 * Event for when an order gets processed.
 */
public class ProcessedOrderEvent extends ApplicationEvent {

    /**
     * The user whose order is processed.
     */
    private User user;

    /**
     * The details of the order that was processed.
     */
    private OrderDetails orderDetails;

    /**
     * Creates an instance of processed order event.
     *
     * @param user the user whose order is processed.
     * @param orderDetails the details of the order.
     */
    public ProcessedOrderEvent(User user, OrderDetails orderDetails) {
        super(orderDetails);
        this.user = user;
        this.orderDetails = orderDetails;
    }

    /**
     * Returns the user whose order is processed.
     *
     * @return the user whose order is processed.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the details of the order that got processed.
     *
     * @return the details of the order that got processed.
     */
    public OrderDetails getOrderDetails() {
        return orderDetails;
    }
}
