package no.ntnu.appdevapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Represents a shopping session.
 */
@Entity
@Table(name = "shopping_session")
public class ShoppingSession {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(unique = true, name = "session_id")
  private long id;

  @ApiModelProperty("The the user belonging to this shopping session.")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "user_id")
  private User user;

  @ApiModelProperty("Set of products in the shopping session.")
  @JsonManagedReference
  @OneToMany(mappedBy = "shoppingSession")
  private final Set<CartItem> cart = new HashSet<>();

  @ApiModelProperty("The total cost of the items in this shopping session.")
  private double total;

  @ApiModelProperty("The total amount of products in this shopping session")
  private int quantity;

  @ApiModelProperty("When the shopping session was created.")
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @ApiModelProperty("When the shopping session was last updated.")
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  /**
   * Creates an instance of shopping session.
   *
   * @param user user of this shopping session.
   */
  public ShoppingSession(User user) {
    this.user = user;
    this.createdAt = LocalDateTime.now();
  }

  /**
   * Empty constructor.
   */
  public ShoppingSession() {

  }

  /**
   * Returns the id of this shopping session.
   *
   * @return id of this shopping session.
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the id of this shopping session.
   *
   * @param id the id to be set for this shopping session.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Returns the user of this shopping session.
   *
   * @return user of this shopping session.
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user for this shopping session.
   *
   * @param user the user to be set for this shopping session.
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Returns the set of cart items in this shopping session.
   *
   * @return set of cart items in this shopping session.
   */
  public Set<CartItem> getCart() {
    return this.cart;
  }

  /**
   * Returns the total cost of this shopping session.
   *
   * @return total cost of this shopping session.
   */
  public double getTotal() {
    return this.total;
  }

  /**
   * Sets the total cost of this shopping session.
   */
  public void updateTotal() {
    if (this.cart.isEmpty()) {
      this.total = 0;
    } else {
      this.total = this.cart.stream().mapToDouble(CartItem::getTotal).sum();
    }
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
    if (this.cart.isEmpty()) {
      this.quantity = 0;
    } else {
      this.quantity = this.cart.stream().mapToInt(CartItem::getQuantity).sum();
    }
  }

  /**
   * Returns when this shopping sessions was created.
   *
   * @return when this shopping session was created.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets when this shopping session was created.
   *
   * @param createdAt the date to be set for when this shopping session was created.
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns when this shopping session was last updated.
   *
   * @return when this shopping session was last updated.
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets when this shopping session was last updated.
   *
   * @param updatedAt the date to be set for when this shopping session was last updated.
   */
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
