package no.ntnu.appdevapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Represents a cart item in a shopping session.
 */
@Entity
@Table(name = "cart_item")
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "cart_item_id")
  private long id;

  @ApiModelProperty("The shopping session for this cart item.")
  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "session_id")
  private ShoppingSession shoppingSession;

  @ApiModelProperty("The product of this cart item.")
  @OneToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "product_id")
  private Product product;

  @ApiModelProperty("Quantity of the associated product.")
  private Integer quantity;

  @ApiModelProperty("When the cart item was last updated.")
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ApiModelProperty("When the cart item was created.")
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /**
   * Creates an instance of cart item.
   *
   * @param shoppingSession the shopping session this cart item belongs to.
   * @param product         the product this cart item represents.
   */
  public CartItem(ShoppingSession shoppingSession, Product product) {
    this.shoppingSession = shoppingSession;
    this.product = product;
    this.quantity = 1;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  /**
   * Empty constructor.
   */
  public CartItem() {

  }

  /**
   * Returns this cart items id.
   *
   * @return this cart items id.
   */
  public long getId() {
    return this.id;
  }

  /**
   * Return this cart items shopping session.
   *
   * @return this cart items shopping session.
   */
  public ShoppingSession getShoppingSession() {
    return shoppingSession;
  }

  /**
   * Sets this cart items shopping session.
   *
   * @param shoppingSession the shopping session to be set for this cart item.
   */
  public void setShoppingSession(ShoppingSession shoppingSession) {
    this.shoppingSession = shoppingSession;
  }

  /**
   * Returns the product this cart item represents.
   *
   * @return product this cart item represents.
   */
  public Product getProduct() {
    return product;
  }

  /**
   * Sets the product that this cart item represents.
   *
   * @param product the product that this cart item is to represent.
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * Returns the quantity of the product in this cart item.
   *
   * @return quantity of the product in this cart item.
   */
  public Integer getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity of the product in this cart item.
   *
   * @param quantity quantity of the product in this cart item.
   */
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  /**
   * Increases the quantity by one.
   */
  public void increaseQuantity() {
    this.quantity += 1;
  }


  /**
   * Returns the calculated total for this cart item.
   *
   * @return total of this cart item.
   */
  public double getTotal() {
    return product.getPrice() * quantity;
  }

  /**
   * Returns when this cart item was last updated.
   *
   * @return when this cart item was last updated.
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets when this cart item was last updated to now.
   */
  public void setUpdatedAt() {
    this.updatedAt = LocalDateTime.now();
  }

  /**
   * Returns when this cart item was created.
   *
   * @return when this cart item was created.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
