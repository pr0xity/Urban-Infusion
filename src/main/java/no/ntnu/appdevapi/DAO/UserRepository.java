package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findByEmail(String email);

  User findById(long id);
}
