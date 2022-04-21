package no.ntnu.appdevapi.entities;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @ApiModelProperty("Name of the product.")
  private String name;
  @ApiModelProperty("Some info about the product.")
  private String description;
  @ApiModelProperty("Where the product originates from.")
  private String origin;
  @ApiModelProperty("Price of the product, decimal in NOK.")
  private double price;
  @ApiModelProperty("Category id of the product.")
  @Column(name = "fk_category_id")
  private int categoryId;
  @ApiModelProperty("Inventory id of the product.")
  @Column(name = "fk_inventory_id")
  private int inventoryId;
  @ApiModelProperty("When the product was created.")
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @ApiModelProperty("When the product was last updated.")
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  @ApiModelProperty("When the product was deleted.")
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public Product(int id, String name, String description, String origin, double price, int categoryId, int inventoryId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.origin = origin;
    this.price = price;
    this.categoryId = categoryId;
    this.inventoryId = inventoryId;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.deletedAt = null;
  }

  public Product(int id, String name, String description, String origin, double price, int categoryId, int inventoryId, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.origin = origin;
    this.price = price;
    this.categoryId = categoryId;
    this.inventoryId = inventoryId;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.deletedAt = null;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  public Product() {}

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public int getInventoryId() {
    return inventoryId;
  }

  public void setInventoryId(int inventoryId) {
    this.inventoryId = inventoryId;
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

  public int getId() {
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
}