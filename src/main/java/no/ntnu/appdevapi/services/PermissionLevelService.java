package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.PermissionLevel;

public interface PermissionLevelService {
    PermissionLevel findByAdminType(String adminType);

    public PermissionLevel savePermissionLevel(PermissionLevel permissionLevel);
}