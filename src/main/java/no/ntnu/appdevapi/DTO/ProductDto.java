package no.ntnu.appdevapi.DTO;

import java.time.LocalDateTime;

/**
 * Data transfer object for a product
 */
public class ProductDto {
  //Name of the product
  private String name;
  //Description of the product
  private String description;
  //The products country/place of origin
  private String origin;
  //Price of the product
  private double price;
  //The inventory amount of the product
  private int inventory;
  //When the product was added to the database
  private LocalDateTime createdAt;
  //When the product was last updated in the database
  private LocalDateTime updatedAt;
  //When the product was marked as inactive
  private LocalDateTime deletedAt;
  //Whether the product is inactive or not
  private boolean inactive;
  //The id of the image associated with the product
  private long imageId;
  //The category of the product
  private String category;

  //Empty constructor
  public ProductDto(){
    this.createdAt = LocalDateTime.now();
  }

  /**
   * Creates an instance of a ProductDTO
   *
   * @param name Name of the product
   * @param description Description of the product
   * @param origin The products country/place of origin
   * @param price Price of the product
   * @param inventory The inventory amount of the product
   * @param imageId The id of the image associated with the product
   * @param category The category of the product
   */
  public ProductDto(String name, String description, String origin, double price, int inventory, long imageId, String category) {
    this.name = name;
    this.description = description;
    this.origin = origin;
    this.price = price;
    this.inventory = inventory;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
    this.deletedAt = null;
    this.inactive = false;
    this.imageId = imageId;
    this.category = category;
  }



  //Getters and setters:

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

  public long getImageId() {
    return imageId;
  }

  public String getCategory() {
    return category;
  }

  /**
   * Checks whether product is inactive or not
   * @return true if product is inactive, false if not
   */
  public boolean isInactive() {
    return inactive;
  }

  public void setInactive(boolean inactive) {
    this.inactive = inactive;
  }
}
