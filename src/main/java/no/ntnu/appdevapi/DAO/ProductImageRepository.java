package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ProductImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Repository interface for product images.
 */
@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, Long> {

    ProductImage findByProduct(Product product);

    @Transactional
    ProductImage deleteByProduct(Product product);

}
