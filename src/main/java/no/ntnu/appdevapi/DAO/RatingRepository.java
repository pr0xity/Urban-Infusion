package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.Rating;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Repository interface for ratings.
 */
public interface RatingRepository extends CrudRepository<Rating, Integer> {
    List<Rating> findByProduct(Product product);
}
