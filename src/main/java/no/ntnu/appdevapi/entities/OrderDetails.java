
package no.ntnu.appdevapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an order and its details.
 */
@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_details_id")
    private long id;

    @ApiModelProperty("The the user belonging to this order.")
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @ApiModelProperty("Set of products in the shopping session.")
    @JsonManagedReference
    @OneToMany(mappedBy = "orderDetails")
    private Set<OrderItem> orderItems = new HashSet<>();

    @ApiModelProperty("The total cost of this order")
    private double total;

    @ApiModelProperty("The total amount of products in this order")
    private int quantity;

    @ApiModelProperty("If this order is processed or not.")
    private boolean processed;

    @ApiModelProperty("When the order was created.")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty("When the order was last updated.")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Creates an instance of order details.
     *
     * @param user the user of this order.
     */
    public OrderDetails(User user, double total, int quantity) {
        this.user = user;
        this.total = total;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Empty constructor
     */
    public OrderDetails () {

    }

    /**
     * Returns the id of this order details.
     *
     * @return id of this order details.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the user of this order details.
     *
     * @return user of this order details.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user of this order details.
     *
     * @param user the user to be set for this order details.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the user of this order details.
     *
     * @return user of this order details.
     */
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Sets the set of order items of this order details.
     *
     * @param orderItems set of order items to be set for this order details.
     */
    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * Returns the total cost of this order.
     *
     * @return total cost of this order.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total cost of this order.
     */
    public void setTotal() {
        this.total = this.orderItems.stream().mapToDouble(OrderItem::getTotal).sum();
    }

    /**
     * Returns the quantity of products in this order.
     *
     * @return quantity of products in this order.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Updates the quantity of products in this order.
     */
    public void updateQuantity() {
        this.quantity = this.orderItems.stream().mapToInt(OrderItem::getQuantity).sum();
    }

    /**
     * Returns if this order is processed or not.
     *
     * @return whether this order is processed or not.
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     * Sets whether this order is processed or not.
     *
     * @param processed true if processed, false if not.
     */
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    /**
     * Returns when this order details was created.
     *
     * @return when this order details was created.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Set when this order details was created.
     *
     * @param createdAt the date this order detail was created.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns when this order details was last updated.
     *
     * @return when this order details was last updated.
     */
    public LocalDateTime getUpdatedAt() {
        if (this.updatedAt == null) {
            return this.createdAt;
        }
        return updatedAt;
    }

    /**
     * Sets when the order details was last updated to now.
     */
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
