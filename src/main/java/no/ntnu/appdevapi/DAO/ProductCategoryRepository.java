package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.ProductCategory;
import org.springframework.data.repository.CrudRepository;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

  ProductCategory findByName(String name);


}
