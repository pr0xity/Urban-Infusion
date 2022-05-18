package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for verification tokens.
 */
@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findFirstByUser(User user);
}
