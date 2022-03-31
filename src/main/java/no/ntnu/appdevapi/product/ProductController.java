package no.ntnu.appdevapi.product;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("products")
public class ProductController {

  @Autowired
  private ProductService productService;


  /**
   * Returns all product in the store.
   *
   * @return List of all products.
   */
  @GetMapping
  @ApiOperation(value = "Get all products.")
  public Iterable<Product> getAll() {
    return productService.getAllProducts();
  }

  /**
   * Get a specific product.
   *
   * @param index The index of the product, starting from 0.
   * @return The product matching the index, or status 404.
   */
  @GetMapping("/{index}")
  @ApiOperation(value = "Get a specific product.", notes = "Returns the product or null when index is invalid.")
  public ResponseEntity<Product> get(@ApiParam("Index of the product.") @PathVariable int index) {
    ResponseEntity<Product> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    Product product = productService.getProduct(index);
    if (null != product) {
      response = new ResponseEntity<>(product, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Add a product to the store.
   *
   * @param product The product to add.
   * @return 200 when added, 400 on error.
   */
  @PostMapping
  @ApiOperation(value = "Add a new product.", notes = "Status 200 when added, 400 on error.")
  public ResponseEntity<String> add(@RequestBody Product product) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (null != product) {
      productService.addProduct(product);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete a product from the store
   *
   * @param index Index of the product to delete.
   * @return 200 when deleted, 404 if not.
   */
  @DeleteMapping("/{index}")
  @ApiIgnore
  public ResponseEntity<String> delete(@PathVariable int index) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if (null != productService.getProduct(index)) {
      productService.deleteProduct(index);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }
}
