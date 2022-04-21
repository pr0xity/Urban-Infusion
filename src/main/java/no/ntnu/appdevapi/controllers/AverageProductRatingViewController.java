package no.ntnu.appdevapi.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import no.ntnu.appdevapi.entities.AverageProductRatingView;
import no.ntnu.appdevapi.services.AverageProductRatingViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for average product view.
 */
@RestController
@RequestMapping("/avgrating")
public class AverageProductRatingViewController {

  @Autowired
  private AverageProductRatingViewService averageProductRatingViewService;

  /**
   * Get all average product rating.
   * @return list of all average product rating.
   */
  @GetMapping
  @ApiOperation(value = "Get all average ratings.")
  public List<AverageProductRatingView> getAll() {
    return averageProductRatingViewService.getAllAvgRating();
  }

  /**
   * Retrieves the average product rating of a given product id.
   * @param index id of the product to get.
   * @return the average rating of the given product and 200 OK, or 404 Not found on failure.
   */
  @GetMapping("/{index}")
  @ApiOperation(value = "Get average rating of a specific product.", notes = "Returns the average rating or null when index is invalid.")
  public ResponseEntity<AverageProductRatingView> get(@ApiParam("Index of product.") @PathVariable int index) {
    ResponseEntity<AverageProductRatingView> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    AverageProductRatingView avgRating = averageProductRatingViewService.getAvgRating(index);
    if (null != avgRating) {
      response = new ResponseEntity<>(avgRating, HttpStatus.OK);
    }
    return response;
  }
}
