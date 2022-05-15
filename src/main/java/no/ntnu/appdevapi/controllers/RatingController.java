package no.ntnu.appdevapi.controllers;

import io.swagger.annotations.ApiOperation;
import no.ntnu.appdevapi.DTO.RatingDto;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.Rating;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.services.ProductService;
import no.ntnu.appdevapi.services.RatingService;
import no.ntnu.appdevapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * REST API controller for ratings.
 */
@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ProductService productService;

    /**
     * Returns 200 OK with list of all ratings.
     *
     * @return list of all rating
     */
    @GetMapping()
    public ResponseEntity<List<Rating>> getAll() {
        List<Rating> ratings = ratingService.getAllRatings();
        ResponseEntity<List<Rating>> response;

        if (ratings != null) {
            response = new ResponseEntity<>(ratings, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Returns the five most recently created ratings.
     *
     * @return List of the five newest ratings.
     */
    @RequestMapping(value = "/recent", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "Get the five most recent ratings.")
    @ResponseBody
    public List<Rating> getRecent() {
        List<Rating> ratings = ratingService.getAllRatings();
        ratings.sort(Comparator.comparing(Rating::getUpdatedAt).reversed());
        int k = ratings.size();
        if (k > 5) {
            ratings.subList(5,k).clear();
        }
        return ratings;
    }

    /**
     * Returns the requested rating.
     *
     * @param ratingId the ID of the requested rating.
     * @return The requested rating.
     */
    @GetMapping("/{ratingId}")
    public ResponseEntity<Rating> getRating(@PathVariable long ratingId) {
        ResponseEntity<Rating> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Rating rating = ratingService.getRating(ratingId);

        if (rating != null) {
            response = new ResponseEntity<>(rating, HttpStatus.OK);
        }
        return response;
    }

    /**
     * Adds the given rating to the product with the given product id.
     *
     * @param productId the product id to add rating to.
     * @param ratingDto the rating to add to the product.
     * @return 200 OK if rating was added, 400 Bad request if it was not.
     */
    @PostMapping("/{productId}")
    public ResponseEntity<String> add(@PathVariable long productId, @RequestBody RatingDto ratingDto) {
        ResponseEntity<String> response;

        if ( ratingDto != null && productService.getProduct(productId) != null ) {
            User user = userService.findOneByEmail(ratingDto.getEmail());

            if ( user != null ) {
                this.ratingService.addRating( new Rating(user, ratingDto.getDisplayName(), productService.getProduct(productId), ratingDto.getRating(), ratingDto.getComment()) );
                response = new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Updates the rating on the product with the given product id, with the given rating.
     *
     * @param productId the product id to update rating on.
     * @param ratingDto the rating to update to.
     * @return 200 OK if rating was updated, 400 Bad request if rating did not already exist.
     */
    @PutMapping("/{productId}")
    public ResponseEntity<String> update(@PathVariable long productId, @RequestBody RatingDto ratingDto) {
        ResponseEntity<String> response;

        Product product = productService.getProduct(productId);
        User user = userService.findOneByEmail(ratingDto.getEmail());

        if (doesRatingAlreadyExist(productId, user)) {
            Rating rating = this.ratingService.getRatingFromUserAndProduct(user, product);
            rating.setDisplayName(ratingDto.getDisplayName());
            rating.setRating(ratingDto.getRating());
            rating.setComment(ratingDto.getComment());
            rating.setUpdatedAt(LocalDateTime.now());
            this.ratingService.updateRating(rating.getId(), rating);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Deletes the user with the given emails rating for the product with the given product id.
     *
     * @param productId product id of the product to delete rating on.
     * @param ratingDto the rating to delete.
     * @return 200 OK if rating was deleted, 400 Bad request if rating did not exist.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> delete(@PathVariable long productId, @RequestBody RatingDto ratingDto) {
        ResponseEntity<String> response;

        Product product = productService.getProduct(productId);
        User user = userService.findOneByEmail(ratingDto.getEmail());
        Rating rating = this.ratingService.getRatingFromUserAndProduct(user, product);

        if (doesRatingAlreadyExist(productId, user)) {
            ratingService.deleteRating(rating.getId());
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Checks if the given user already has an existing rating on the product with the
     * given product id.
     *
     * @param productId the product id of the product to check rating on.
     * @param user the user to check for existing rating.
     * @return true if rating exists, false if not.
     */
    private boolean doesRatingAlreadyExist(long productId, User user) {
        Product product = productService.getProduct(productId);
        Rating rating = this.ratingService.getRatingFromUserAndProduct(user, product);
        return rating != null;
    }
}
