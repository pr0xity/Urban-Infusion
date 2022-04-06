package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.PermissionLevelRepository;
import no.ntnu.appdevapi.entities.PermissionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionLevelServiceImpl implements PermissionLevelService {

    @Autowired
    private PermissionLevelRepository permissionLevelRepository;

    @Override
    public PermissionLevel findByAdminType(String adminType) {
        return permissionLevelRepository.findPermissionLevelByAdminType(adminType);
    }
}
