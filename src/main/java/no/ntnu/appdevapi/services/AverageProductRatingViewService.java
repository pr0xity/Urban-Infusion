package no.ntnu.appdevapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import no.ntnu.appdevapi.DAO.AverageProductRatingViewRepository;
import no.ntnu.appdevapi.entities.AverageProductRatingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic for average product rating view.
 */
@Service
public class AverageProductRatingViewService {
  @Autowired
  private AverageProductRatingViewRepository averageProductRatingViewRepository;

  /**
   * Returns a list of all average ratings.
   * @return list of all average ratings.
   */
  public List<AverageProductRatingView> getAllAvgRating() {
    List<AverageProductRatingView> avgRating = new ArrayList<>();
    averageProductRatingViewRepository.findAll().forEach(avgRating::add);
    return avgRating;
  }

  /**
   * return the average rating of a given product.
   * @param index id of the product to find.
   * @return the average rating for the given product or null if not found.
   */
  public AverageProductRatingView getAvgRating(int index) {
    Optional<AverageProductRatingView> avgRating = averageProductRatingViewRepository.findById(index);
    return avgRating.orElse(null);
  }
}
