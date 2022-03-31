package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.UserAddress;
import org.springframework.data.repository.CrudRepository;

public interface UserAddressRepository extends CrudRepository<UserAddress, Integer> {

}
