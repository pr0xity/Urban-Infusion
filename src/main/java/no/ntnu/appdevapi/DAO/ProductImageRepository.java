package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.ProductImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for product images.
 */
@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, Long> {
}
