package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
