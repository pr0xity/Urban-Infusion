package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.Product;

public interface ProductService {
  Iterable<Product> getAllProducts();

  Product getProduct(long id);

  Product addProduct(Product product);

  void deleteProduct(long id);
}
