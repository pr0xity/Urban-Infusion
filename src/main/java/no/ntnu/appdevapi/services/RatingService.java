package no.ntnu.appdevapi.services;

import java.util.List;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.Rating;
import no.ntnu.appdevapi.entities.User;

public interface RatingService {
  List<Rating> getAllRatings();

  List<Rating> getRatingsFromProduct(Product product);

  Rating getRatingFromUserAndProduct(User user, Product product);

  double getAverageRatingFromProduct(Product product);

  Rating getRating(long id);

  Rating addRating(Rating rating);

  void updateRating(long id, Rating rating);

  void deleteRating(long id);
}
