package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.ProductCategory;

public interface ProductCategoryService {


  Iterable<ProductCategory> getAllProductCategories();

  ProductCategory getProductCategory(long id);

  void addProductCategory(ProductCategory productCategory);

  void deleteProductCategory(long id);
}
