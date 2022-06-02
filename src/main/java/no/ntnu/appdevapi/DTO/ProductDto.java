package no.ntnu.appdevapi.DTO;

import java.time.LocalDateTime;

public class ProductDto {

  private String name;
  private String description;
  private String origin;
  private double price;
  private int inventory;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
  private boolean inactive;
  private String category;


  public ProductDto(){
    this.createdAt = LocalDateTime.now();

  }

  public ProductDto(String name, String description, String origin, double price, int inventory, String category) {
    this.name = name;
    this.description = description;
    this.origin = origin;
    this.price = price;
    this.inventory = inventory;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.deletedAt = null;
    this.inactive = false;
    this.category = category;
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

  public int getInventory() {
    return inventory;
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

  public String getCategory() {
    return category;
  }

  public boolean isInactive() {
    return inactive;
  }

  public void setInactive(boolean inactive) {
    this.inactive = inactive;
  }
}
