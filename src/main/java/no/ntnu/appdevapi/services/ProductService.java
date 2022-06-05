package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DTO.ProductDto;
import no.ntnu.appdevapi.entities.Product;

public interface ProductService {
  Iterable<Product> getAllProducts();

  Iterable<Product> getAllProductsNotDeleted();

  Product getProduct(long id);

  Product getProductByName(String name);

  Product addProductFromDto(ProductDto product);

  Product updateProduct(long id, ProductDto product);

  /**
   * Updates the product that has the given product with given product.
   *
   * @param productId id of the product to update.
   * @param product the product object to update to.
   * @return the updated product object.
   */
  Product updateProductWithProductObject(long productId, Product product);

  void deleteProduct(long id);
}
