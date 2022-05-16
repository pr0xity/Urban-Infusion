package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.Rating;
import no.ntnu.appdevapi.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ratings.
 */
@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {
    List<Rating> findByProduct(Product product);

    Optional<Rating> findByUserAndProduct(User user, Product product);

    Rating findFirstByUserproduct(String userProduct);

}
