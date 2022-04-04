package no.ntnu.appdevapi.DAO;

import no.ntnu.appdevapi.entities.PermissionLevel;
import org.springframework.data.repository.CrudRepository;

public interface PermissionLevelRepository extends CrudRepository<PermissionLevel, Integer> {
}
