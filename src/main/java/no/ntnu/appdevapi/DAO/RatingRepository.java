package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.Rating;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Repository interface for ratings.
 */
public interface RatingRepository extends CrudRepository<Rating, Long> {
    List<Rating> findByProduct(Product product);

    Rating findFirstByUserproduct(String userProduct);

}
