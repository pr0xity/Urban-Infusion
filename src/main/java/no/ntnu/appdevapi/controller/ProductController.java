package no.ntnu.appdevapi.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.LinkedList;
import java.util.List;
import no.ntnu.appdevapi.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("products")
public class ProductController {

  private static final List<Product> products = new LinkedList<>();


  /**
   * Returns all product in the store.
   *
   * @return List of all products.
   */
  @GetMapping
  @ApiOperation(value = "Get all products.")
  public List<Product> getAll() {
    fillListWithProducts();
    return products;
  }

  /**
   * Get a specific product.
   *
   * @param index The index of the product, starting from 0.
   * @return The product matching the index, null otherwise.
   */
  @GetMapping("/{index}")
  @ApiOperation(value = "Get a specific product.", notes = "Returns the product or null when index is invalid.")
  public ResponseEntity<Product> get(@ApiParam("Index of the product.") @PathVariable int index) {
    fillListWithProducts();
    ResponseEntity<Product> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if (index >= 0 && index < products.size()) {
      Product product = products.get(index);
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
    fillListWithProducts();
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (product != null) {
      products.add(product);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }

  /**
   * Delete a product from the store
   *
   * @param index Index or the product to delete, starting from 0.
   * @return 200 when deleted, 404 if not.
   */
  @DeleteMapping("/{index}")
  @ApiIgnore
  public ResponseEntity<String> delete(@PathVariable int index) {
    fillListWithProducts();
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if (index >= 0 && index < products.size()) {
      products.remove(index);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }

  private void fillListWithProducts() {
    if (products.isEmpty()) {
      products.add(new Product("Heather tea", 200));
      products.add(new Product("Linden blossom tea", 200));
      products.add(new Product("Sencha", 800));
      products.add(new Product("Mug", 120));
    }
  }

}
