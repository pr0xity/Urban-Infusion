package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.OrderDetails;
import no.ntnu.appdevapi.entities.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for order items.
 */
@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    @Query(value = "select product_id from order_item group by product_id order by sum(quantity) desc limit 3", nativeQuery = true)
    List<Long> findTopBestSellingProducts();

    List<OrderItem> findAllByOrderDetails(OrderDetails orderDetails);
}
