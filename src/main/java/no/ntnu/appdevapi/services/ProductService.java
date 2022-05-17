package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DTO.ProductDto;
import no.ntnu.appdevapi.entities.Product;

public interface ProductService {
  Iterable<Product> getAllProducts();

  Product getProduct(long id);

  Product getProductByName(String name);

  Product addProductFromDto(ProductDto product);

  void deleteProduct(long id);
}
