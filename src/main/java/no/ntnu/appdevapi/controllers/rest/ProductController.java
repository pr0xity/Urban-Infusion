package no.ntnu.appdevapi.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import no.ntnu.appdevapi.DTO.ProductDto;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ProductImage;
import no.ntnu.appdevapi.services.ProductImageService;
import no.ntnu.appdevapi.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;


@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductServiceImpl productService;

  @Autowired
  private ProductImageService productImageService;


  /**
   * Returns all product in the store.
   *
   * @return List of all products.
   */
  @GetMapping
  @ApiOperation(value = "Get all products.")
  public Iterable<Product> getAllNotDeleted() {
    return productService.getAllProductsNotDeleted();
  }

  @GetMapping("/all")
  @ApiOperation(value = "Get all products for admin page")
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
  public ResponseEntity<Product> get(@ApiParam("Index of the product.") @PathVariable long index) {
    ResponseEntity<Product> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    Product product = productService.getProduct(index);
    if (null != product && product.getDeletedAt() == null) {
      response = new ResponseEntity<>(product, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Returns the image with the given image id.
   *
   * @param imageId id of the image to return.
   * @return 200 ok with the image with the given image id, or 404 not found if not found.
   */
  @GetMapping("/images/{imageId}")
  @ApiOperation(value = "Gets image of product", notes = "Status 200 and image if found, 404 if product or image not found")
  public ResponseEntity<byte[]> getImage(@PathVariable long imageId) {
    ProductImage image = productImageService.getImageById(imageId);

    if (image != null) {
      return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_TYPE, image.getContentType())
              .body(image.getData());
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /**
   * Add a product to the store.
   *
   * @param product The product to add.
   * @return 200 when added, 400 on error.
   */
  @PostMapping
  @ApiOperation(value = "Add a new product.", notes = "Status 200 when added, 400 on error.")
  public ResponseEntity<?> add(@RequestBody ProductDto product) {
    if (null != product) {
      productService.addProductFromDto(product);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }


  @PutMapping("/{id}")
  @ApiOperation(value = "Update existing product.", notes = "Status 200 when updated, 400 on error.")
  public ResponseEntity<String> update(@PathVariable long id, @RequestBody ProductDto product) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
    if (null == product) {
      response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else if (null == productService.getProduct(id)) {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      productService.updateProduct(id, product);
    }
    return response;
  }

  /**
   * Adds a product image.
   *
   * @param imageFile the image file to add.
   * @return 200 ok with the image id on success, 400 bad request on error.
   */
  @PostMapping("/images")
  @ApiOperation(value = "Adds a product image", notes = "Status 200 when added, 400 on error.")
  public ResponseEntity<?> addImage(@RequestParam("imageFile") MultipartFile imageFile) {
      if (imageFile != null) {
        ProductImage imageAdded = productImageService.addImage(imageFile);
        long imageId = imageAdded.getId();
        return new ResponseEntity<>("" + imageId, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }


  /**
   * Updates the product image of the product with the given id.
   *
   * @param productId id of the product whose image is to be updated.
   * @param imageFile the image file to update to.
   * @return 200 ok if the image was added, 404 not found if a product with the product id was not found.
   */
  @PutMapping("/images/{productId}")
  @ApiOperation(value = "Update image of product", notes = "Status 200 when successfully updated, 404 if product not found, 400 on error.")
  public ResponseEntity<String> updateImage(@PathVariable long productId, @RequestParam("imageFile") MultipartFile imageFile) {
    Product product = productService.getProduct(productId);
    if (product == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    ProductImage productImage = productImageService.getImageById(product.getImageId());
    if (productImage != null) {
      productImageService.updateImage(product.getImageId(), imageFile);
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      ProductImage addedImage = productImageService.addImage(imageFile);
      product.setImageId(addedImage.getId());
      productService.updateProductWithProductObject(productId, product);
      return new ResponseEntity<>("" + addedImage.getId(), HttpStatus.OK);
    }
  }

  /**
   * Delete a product from the store
   *
   * @param index Index of the product to delete.
   * @return 200 when deleted, 404 if not.
   */
  @DeleteMapping("/{index}")
  @ApiIgnore
  public ResponseEntity<String> delete(@PathVariable long index) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if (null != productService.getProduct(index)) {
      productService.deleteProduct(index);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }
}
