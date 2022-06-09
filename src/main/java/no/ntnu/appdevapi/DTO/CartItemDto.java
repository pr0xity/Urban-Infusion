package no.ntnu.appdevapi.DTO;

/**
 * Data transfer object of a cart item.
 */
public class CartItemDto {

  /**
   * The id of the product of this cart item dto.
   */
  private final Long productId;

  /**
   * The quantity of the product in this cart item.
   */
  private final Integer quantity;

  /**
   * Creates an instance of cart item dto.
   *
   * @param productId the id of the product of this cart item dto.
   * @param quantity  the quantity of the product in this cart item dto.
   */
  public CartItemDto(Long productId, Integer quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  /**
   * Returns the id of the product in this cart item dto.
   *
   * @return the id of the product in this cart item dto.
   */
  public Long getProductId() {
    return productId;
  }

  /**
   * Returns the quantity of the product in this cart item dto.
   *
   * @return the quantity of the product in this cart item dto.
   */
  public Integer getQuantity() {
    return quantity;
  }
}
