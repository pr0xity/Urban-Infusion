package no.ntnu.appdevapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(unique = true, name = "product_id")
  private long id;
  @ApiModelProperty("Name of the product.")
  private String name;
  @ApiModelProperty("Some info about the product.")
  private String description;
  @ApiModelProperty("Where the product originates from.")
  private String origin;
  @ApiModelProperty("Price of the product, decimal in NOK.")
  private double price;
  @ApiModelProperty("Inventory id of the product.")
  @Column(name = "inventory")
  private int inventory;

  @ApiModelProperty("Id of the image connected to this product")
  @Column(name = "product_image_id")
  private long imageId;

  @ApiModelProperty("When the product was created.")
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @ApiModelProperty("When the product was last updated.")
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  @ApiModelProperty("When the product was deleted.")
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;
  @ApiModelProperty("Show if product is inactive or active")
  private boolean inactive;

  @OneToMany(mappedBy = "product")
  @JsonIgnore
  List<Rating> ratings;

  @ApiModelProperty("Category id of the product.")
  @ManyToOne (cascade = CascadeType.DETACH)
  @JoinColumn(name= "category_id")
  private ProductCategory category;


  public ProductCategory getCategory() {
    return category;
  }

  public void setCategory(ProductCategory category) {
    this.category = category;
  }

  public int getInventory() {
    return inventory;
  }

  public void setInventory(int inventoryId) {
    this.inventory = inventoryId;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public long getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public boolean isInactive() {
    return inactive;
  }

  public void setInactive(boolean inactive) {
    this.inactive = inactive;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Returns the id of the product image connected to this product.
   *
   * @return id of the product image connected to this product
   */
  public long getImageId() {
    return imageId;
  }

  /**
   * Sets the id for what product image is to be connected to this product.
   *
   * @param imageId id for the product image to be connected to this product.
   */
  public void setImageId(long imageId) {
    this.imageId = imageId;
  }

  /**
   * Returns the average rating.
   *
   * @return the average rating.
   */
  public double getAverageRating() {
    return this.ratings.stream().mapToDouble(Rating::getRating).average().orElse(0.0);
  }

  public List<Rating> getRatings() {
    return this.ratings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return id == product.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}