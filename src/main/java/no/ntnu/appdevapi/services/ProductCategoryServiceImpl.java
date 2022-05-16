package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.ProductCategoryRepository;
import no.ntnu.appdevapi.entities.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCategoryServiceImpl {

  @Autowired
  private ProductCategoryRepository productCategoryRepository;

  public Iterable<ProductCategory> getAllProductCategories(){
    return productCategoryRepository.findAll();
  }

  public ProductCategory getProductCategory(long id){
    Optional<ProductCategory> category = productCategoryRepository.findById(id);
    return category.orElse(null);
  }

  public void addProductCategory(ProductCategory productCategory){
    if (!productCategoryRepository.existsById(productCategory.getId())) {
      productCategoryRepository.save(productCategory);
    }
  }

  public void deleteProductCategory(long id){
    if(productCategoryRepository.existsById(id)){
      productCategoryRepository.deleteById(id);
    }
  }

}
