package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
