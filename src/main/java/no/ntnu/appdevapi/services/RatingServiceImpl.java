package no.ntnu.appdevapi.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import no.ntnu.appdevapi.DAO.ProductRepository;
import no.ntnu.appdevapi.DAO.RatingRepository;
import no.ntnu.appdevapi.DAO.UserRepository;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.Rating;
import no.ntnu.appdevapi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic for ratings.
 */
@Service
public class RatingServiceImpl implements RatingService {

  @Autowired
  private RatingRepository ratingRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  /**
   * Returns a list of all ratings,
   * sorted first by update date (the newest first),
   * then by comment (empty comments last).
   *
   * @return sorted {@code List<Rating>} of all ratings.
   */
  public List<Rating> getAllRatings() {
    List<Rating> ratings = new ArrayList<>();
    ratingRepository.findAll().forEach(ratings::add);

    return ratings.stream()
            .sorted(Comparator.comparing(Rating::getUpdatedAt))
            .sorted(Comparator.comparing(Rating::getComment).reversed())
            .collect(Collectors.toList());
  }

  /**
   * Gets a list of all ratings of the product with the given id.
   *
   * @param product the product ID to retrieve ratings for.
   * @return {@code List<Rating} of all ratings of the product with the given id.
   */
  public List<Rating> getRatingsFromProduct(Product product) {
    return new ArrayList<>(ratingRepository.findByProduct(product));
  }

  /**
   * Gets the rating of given product by given user.
   *
   * @param user    the author of the rating.
   * @param product the product rated.
   * @return {@code Rating} of given product by given user.
   */
  public Rating getRatingFromUserAndProduct(User user, Product product) {
    return ratingRepository.findByUserAndProduct(user, product).orElse(null);
  }

  /**
   * Gets the average rating of the given product.
   *
   * @param product the product to find the average rating for.
   * @return {@code Double} average rating of the product.
   */
  @Override
  public double getAverageRatingFromProduct(Product product) {
    List<Rating> ratings = getRatingsFromProduct(product);
    return ratings.stream().mapToDouble(Rating::getRating).average().orElse(0.0);
  }

  /**
   * Returns the rating with the given ID.
   *
   * @param id ID of the rating to find.
   * @return the rating with the given id or null if not found.
   */
  public Rating getRating(long id) {
    Optional<Rating> rating = ratingRepository.findById(id);
    return rating.orElse(null);
  }

  /**
   * Adds a rating.
   *
   * @param rating the rating to be added.
   */
  public Rating addRating(Rating rating) {
    if (null == ratingRepository.findFirstByUserproduct(rating.getUserAndProductAsString())) {
      rating.setUser(userRepository.findByEmail(rating.getUser().getEmail()));
      rating.setProduct(productRepository.findByName(rating.getProduct().getName()));
      ratingRepository.save(rating);
    }
    return ratingRepository.findFirstByUserproduct(rating.getUserAndProductAsString());
  }

  /**
   * Updates the rating with the given id.
   *
   * @param id     the id of the rating to update.
   * @param rating the rating to update to.
   */
  public void updateRating(long id, Rating rating) {
    if (rating != null && rating.getId() == id && getRating(id) != null) {
      this.ratingRepository.save(rating);
    }
  }

  /**
   * Deletes the rating with the given id.
   *
   * @param id id of the rating to be deleted.
   */
  public void deleteRating(long id) {
    this.ratingRepository.deleteById(id);
  }
}
