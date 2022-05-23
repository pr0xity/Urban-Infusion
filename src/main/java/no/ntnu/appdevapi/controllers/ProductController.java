package no.ntnu.appdevapi.controllers;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("API/products")
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
   * Gets the image for of the product with the given product id.
   *
   * @param productId the product id of the product to find image for.
   * @return 200 OK on success (with image), 404 not found if product or image was not found.
   */
  @GetMapping("/images/{productId}")
  @ApiOperation(value = "Gets image of product", notes = "Status 200 and image if found, 404 if product or image not found")
  public ResponseEntity<byte[]> getImage(@PathVariable long productId) {
    Product product = productService.getProduct(productId);

    if (product != null) {
      ProductImage image = productImageService.getImageByProduct(product);

      if (image != null) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, image.getContentType())
                .body(image.getData());
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
  public ResponseEntity<String> add(@RequestBody ProductDto product) {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (null != product) {
      productService.addProductFromDto(product);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
    return response;
  }

  /**
   * Adds the given image file to the product with the given id.
   *
   * @param productId the product id to add image to.
   * @param imageFile the image file to add.
   * @return 200 OK on success, 400 bad request on error, 404 not found if product was not found.
   */
  @PostMapping("/images/{productId}")
  @ApiOperation(value = "Add an image to product", notes = "Status 200 when added, 404 if product not found, 400 on error.")
  public ResponseEntity<String> addImage(@PathVariable long productId, @RequestParam("imageFile") MultipartFile imageFile) {
    Product product = productService.getProduct(productId);
    ProductImage image = productImageService.getImageByProduct(product);

    if (product != null) {

      if (image == null && imageFile != null) {
        productImageService.addImage(imageFile, product);
        return new ResponseEntity<>(HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
   * Updates the given image file to the product with the given id.
   *
   * @param productId the product id to update image of.
   * @param part the new image of the product.
   * @return 200 OK on success, 400 bad request on error, 404 not found if product was not found.
   */
  @PutMapping("/images/{productId}")
  @ApiOperation(value = "Update image of product", notes = "Status 200 when successfully updated, 404 if product not found, 400 on error.")
  public ResponseEntity<String> updateImage(@PathVariable long productId, @RequestPart("file") Part part) throws IOException {
    ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    Product p = productService.getProduct(productId);
    if (null != p) {
      //Writing image or file
      String fileName = getFileName(part);
      String[] fileArray = fileName.split("\\.");
      ProductImage image = new ProductImage(part.getInputStream().readAllBytes(), p, fileArray[fileArray.length-1], part.getContentType());
      productImageService.updateImage(productId, image);
      response = new ResponseEntity<>(HttpStatus.OK);
    }
      return response;
  }

  private String getFileName(Part part) {
    String contentDisp = part.getHeader("content-disposition");
    System.out.println("content-disposition header= " + contentDisp);
    String[] tokens = contentDisp.split(";");
    for (String token : tokens) {
      if (token.trim().startsWith("filename")) {
        return token.substring(token.indexOf("=") + 2,
                token.length() - 1);
      }
    }
    return "";
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
