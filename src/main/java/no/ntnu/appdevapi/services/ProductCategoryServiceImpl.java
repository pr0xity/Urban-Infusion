package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.ProductCategoryRepository;
import no.ntnu.appdevapi.entities.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

  @Autowired
  private ProductCategoryRepository productCategoryRepository;

  /**
   * Gets an iterable collection of all product categories.
   *
   * @return {@code Iterable<ProductCategory>} of all product categories.
   */
  public Iterable<ProductCategory> getAllProductCategories(){
    return productCategoryRepository.findAll();
  }

  /**
   * Gets the product category with the given ID.
   *
   * @param id ID of the product category to search for.
   * @return {@code ProductCategory} with given ID, or {@code null} if no match found.
   */
  public ProductCategory getProductCategory(long id){
    Optional<ProductCategory> category = productCategoryRepository.findById(id);
    return category.orElse(null);
  }

  /**
   * Adds the given product category to the database.
   *
   * @param productCategory {@code ProductCategory} to be added.
   */
  public void addProductCategory(ProductCategory productCategory){
    if (!productCategoryRepository.existsById(productCategory.getId())) {
      productCategoryRepository.save(productCategory);
    }
  }

  /**
   * Deletes the product category with the given ID.
   *
   * @param id ID of the category to be deleted.
   */
  public void deleteProductCategory(long id){
    if(productCategoryRepository.existsById(id)){
      productCategoryRepository.deleteById(id);
    }
  }
}
