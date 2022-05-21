package no.ntnu.appdevapi.DTO;

import java.time.LocalDateTime;

public class ProductDto {

  private String name;
  private String description;
  private String origin;
  private double price;
  private int inventoryId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  private String categoryName;
  private String categoryDescription;


  public ProductDto(){
    this.price = 0;
    this.inventoryId = 0;
  }

  public ProductDto(String name, String description, String origin, double price, String category) {
    this.name = name;
    this.description = description;
    this.origin = origin;
    this.price = price;
    this.inventoryId = 0;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.deletedAt = null;

    this.categoryName = category;
  }

  public ProductDto(String name, String description, String origin, double price, String category, String categoryDescription) {
    this.name = name;
    this.description = description;
    this.origin = origin;
    this.price = price;
    this.inventoryId = 0;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.deletedAt = null;

    this.categoryName = category;
    this.categoryDescription = categoryDescription;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getOrigin() {
    return origin;
  }

  public double getPrice() {
    return price;
  }

  public int getInventoryId() {
    return inventoryId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getCategoryDescription() {
    return categoryDescription;
  }
}
