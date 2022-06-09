package no.ntnu.appdevapi.entities;

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

/**
 * Represents a rating made by a user.
 */
@Entity
public class Rating {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(unique = true, name = "rating_id")
  private long id;

  @ApiModelProperty("The user of who made the rating.")
  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "user_id")
  private User user;

  @ApiModelProperty("The display name of the user who made the rating.")
  private String displayName;

  @ApiModelProperty("The product rated.")
  //@JsonBackReference
  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "product_id")
  private Product product;

  @ApiModelProperty("Integer value of this rating.")
  private int rating;

  @ApiModelProperty("Contains the comment for this rating.")
  private String comment;

  @ApiModelProperty("When the rating was last updated.")
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  //User and product as string
  private String userproduct;

  /**
   * Creates an instance of a rating.
   *
   * @param user        the user who made this rating.
   * @param displayName the display name of the user in this rating.
   * @param product     the product this rating belongs to.
   * @param rating      the integer value of this rating.
   * @param comment     the comment of this rating.
   */
  public Rating(User user, String displayName, Product product, int rating, String comment) {
    this.user = user;
    setDisplayName(displayName);
    this.product = product;
    this.rating = rating;
    this.comment = comment;
    this.updatedAt = LocalDateTime.now();
    this.userproduct = user.getEmail() + product.getName();
  }

  /**
   * Empty constructor.
   */
  public Rating() {

  }

  /**
   * Returns the id of this rating.
   *
   * @return the id of this rating.
   */
  public long getId() {
    return id;
  }

  /**
   * Returns the user who made this rating.
   *
   * @return user who made this rating.
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user who made this rating.
   *
   * @param user the user who made this rating.
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Returns the display name for this rating.
   *
   * @return display name for this rating.
   */
  public String getDisplayName() {
    return this.displayName;
  }

  /**
   * Sets the display name for this rating.
   *
   * @param displayName display name to be set for this rating, if empty "anonymous" will be set.
   */
  public void setDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      this.displayName = "Anonymous";
    } else {
      this.displayName = displayName;
    }
  }

  public String getUserAndProductAsString() {
    return userproduct;
  }

  /**
   * Returns the product this rating was made for.
   *
   * @return product this rating was made for.
   */
  public Product getProduct() {
    return product;
  }

  /**
   * Sets the product this rating was made for.
   *
   * @param product the product this rating was made for.
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * Returns the value of this rating.
   *
   * @return value of this rating.
   */
  public int getRating() {
    return rating;
  }

  /**
   * Sets the value of rating.
   *
   * @param rating the value of the rating.
   */
  public void setRating(int rating) {
    this.rating = rating;
  }

  /**
   * Returns the comment of this rating.
   *
   * @return comment of this rating.
   */
  public String getComment() {
    return comment;
  }

  /**
   * Sets the comment of this rating.
   *
   * @param comment comment of this rating.
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * Returns when this rating was last updated.
   *
   * @return when this rating was last updated.
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets when this rating was last updated.
   *
   * @param updatedAt the new date for when this rating was updated.
   */
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
