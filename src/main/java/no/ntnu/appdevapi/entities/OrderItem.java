package no.ntnu.appdevapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents an item in an order;
 */
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_item_id")
    private long id;

    @ApiModelProperty("The order details this order item belongs in.")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_details_id")
    private OrderDetails orderDetails;

    @ApiModelProperty("The product of this order item.")
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;

    @ApiModelProperty("Quantity of the associated product.")
    private Integer quantity;

    @ApiModelProperty("When the order item was created.")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty("When the order item was last updated.")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Creates an instance of order item.
     *
     * @param product the product this order item represents.
     */
    public OrderItem(OrderDetails orderDetails, Product product, int quantity) {
        this.orderDetails = orderDetails;
        this.product = product;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Empty constructor.
     */
    public OrderItem() {

    }

    /**
     * Returns this order items id.
     *
     * @return this order items id.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the order details this order item belongs to.
     *
     * @return order details this order item belongs to.
     */
    public OrderDetails getOrderDetails() {
        return this.orderDetails;
    }

    /**
     * Sets the order details for this order item.
     *
     * @param orderDetails the order details to be set for this order item.
     */
    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    /**
     * Returns the product this order item represents.
     *
     * @return product this order item represents.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product that this order item represents.
     *
     * @param product the product that this order item is to represent.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Returns the quantity of the product in this order item.
     *
     * @return quantity of the product in this order item.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in this order item.
     *
     * @param quantity quantity of the product in this order item.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the calculated total for this order item.
     *
     * @return total of this order item.
     */
    public double getTotal() {
        return product.getPrice() * quantity;
    }

    /**
     * Returns when this order item was last updated.
     *
     * @return when this order item was last updated.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets when this order item was last updated to now.
     */
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Returns when this order item was created.
     *
     * @return when this order item was created.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
