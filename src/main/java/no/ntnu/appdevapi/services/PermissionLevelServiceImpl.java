package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.PermissionLevelRepository;
import no.ntnu.appdevapi.entities.PermissionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionLevelServiceImpl implements PermissionLevelService {

    @Autowired
    private PermissionLevelRepository permissionLevelRepository;

    /**
     * Gets the permission level with the given admin type.
     *
     * @param adminType {@code String} admin type to search for.
     * @return {@code PermissionLevel} with given admin type, or null if no match.
     */
    @Override
    public PermissionLevel findByAdminType(String adminType) {
        return permissionLevelRepository.findPermissionLevelByAdminType(adminType);
    }

    /**
     * Adds the given permission level to the database.
     *
     * @param permissionLevel {@code PermissionLevel} to be added.
     * @return the saved {@code PermissionLevel}.
     */
    @Override
    public PermissionLevel savePermissionLevel(PermissionLevel permissionLevel) {
        if (permissionLevelRepository.findPermissionLevelByAdminType(
                permissionLevel.getAdminType()) == null) {

            return permissionLevelRepository.save(permissionLevel);
        }
        return null;
    }
}
