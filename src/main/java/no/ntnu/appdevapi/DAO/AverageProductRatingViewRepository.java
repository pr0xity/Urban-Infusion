package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.AverageProductRatingView;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for average product rating view.
 */
public interface AverageProductRatingViewRepository extends CrudRepository<AverageProductRatingView, Integer> {

}
