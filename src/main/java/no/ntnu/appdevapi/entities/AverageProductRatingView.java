package no.ntnu.appdevapi.entities;


import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represent an average rating of a product.
 */
@Entity
@Table(name = "avg_rating")
public class AverageProductRatingView {


  @Id
  @ApiModelProperty("Id of the product")
  @Column(name = "fk_product_id")
  private int productId;
  @ApiModelProperty("Average rating")
  @Column(name = "average_rating")
  private double avgRating;

  /**
   * Returns the product id.
   * @return id of the product.
   */
  public int getProductId() {
    return productId;
  }

  /**
   * Returns the average rating of the product.
   * @return average rating of the product.
   */
  public double getAvgRating() {
    return avgRating;
  }
}
