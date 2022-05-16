package no.ntnu.appdevapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a category of a product
 */
@Entity
@Table(name = "product_category")
public class ProductCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(unique = true, name = "category_id")
  private long id;

  @ApiModelProperty("The category of the product")
  @Column(unique = true, name = "name")
  private String name;

  @ApiModelProperty("A description of the category")
  @Column(unique = true, name = "description")
  private String description;

  @ApiModelProperty("When the category was created")
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @ApiModelProperty("When the category was last updated")
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ApiModelProperty("When the category was deleted")
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @OneToMany(mappedBy = "category")
  @JsonIgnore
  private List<Product> products;


  /**
   * Creates an instance of ProductCategory.
   *
   * @param name the name of the category of the product
   * @param description a description of the category
   */
  public ProductCategory(String name, String description) {
    this.name = name;
    this.description = description;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = createdAt;
  }

  public ProductCategory() {
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
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

}
