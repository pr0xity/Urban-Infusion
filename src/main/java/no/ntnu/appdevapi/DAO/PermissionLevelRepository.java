package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.PermissionLevel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionLevelRepository extends CrudRepository<PermissionLevel, Integer> {
    PermissionLevel findPermissionLevelByAdminType(String adminType);
}
