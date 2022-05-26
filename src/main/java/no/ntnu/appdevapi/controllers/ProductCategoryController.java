package no.ntnu.appdevapi.controllers;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ProductCategory;
import no.ntnu.appdevapi.services.ProductCategoryService;
import no.ntnu.appdevapi.services.ProductCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("API/product-categories")
public class ProductCategoryController {

  @Autowired
  private ProductCategoryServiceImpl productCategoryService;


  /**
   * Returns all product categories in the store.
   *
   * @return List of all products categories.
   */
  @GetMapping
  @ApiOperation(value = "Get all product categories")
  public Iterable<ProductCategory> getAll(){return productCategoryService.getAllProductCategories();}



  /**
   * Get a specific category for a product
   *
   * @param index The index of the category, starting from 0.
   * @return The product matching the index, or status 404.
   */
  @GetMapping("/{index}")
  @ApiOperation(value = "Get a specific product category.", notes = "Returns the product category or null when index is invalid.")
  public ResponseEntity<ProductCategory> get(@ApiParam("Index of the category.") @PathVariable long index) {
    ResponseEntity<ProductCategory> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    ProductCategory category = productCategoryService.getProductCategory(index);
    if (null != category) {
      response = new ResponseEntity<>(category, HttpStatus.OK);
    }
    return response;
  }


  /**
   * Add a new product category.
   *
   * @param category The product category to add.
   * @return 200 when added, 400 on error.
   */
  @PostMapping
  @ApiOperation(value = "Add a new product category.", notes = "Status 200 when added, 400 on error.")
  public ResponseEntity<String> add(@RequestBody ProductCategory category) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (null != category) {
      productCategoryService.addProductCategory(category);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete a product category from the store
   *
   * @param index Index of the category to delete.
   * @return 200 when deleted, 404 if not.
   */
  @DeleteMapping("/{index}")
  @ApiOperation(value = "Delete a product category.", notes = "Status 200 when added, 400 on error.")
  public ResponseEntity<String> delete(@PathVariable long index) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if (null != productCategoryService.getProductCategory(index)) {
      productCategoryService.deleteProductCategory(index);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }
}
