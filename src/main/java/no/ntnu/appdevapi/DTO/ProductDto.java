package no.ntnu.appdevapi.DTO;

import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ProductCategory;
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


  public Product getProductFromDto(){
    Product product = new Product();
    product.setName(name);
    product.setDescription(description);
    product.setOrigin(origin);
    product.setPrice(price);
    product.setInventoryId(inventoryId);
    product.setCreatedAt(createdAt);
    product.setUpdatedAt(updatedAt);
    product.setDeletedAt(deletedAt);

    ProductCategory c = new ProductCategory(categoryName,categoryDescription);

    product.setCategory(c);
    return product;
  }

  public String getName() {
    return name;
  }
}
