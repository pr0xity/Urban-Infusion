package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.PermissionLevel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for permission levels.
 */
@Repository
public interface PermissionLevelRepository extends CrudRepository<PermissionLevel, Long> {
    PermissionLevel findPermissionLevelByAdminType(String adminType);
}
