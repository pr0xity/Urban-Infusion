package no.ntnu.appdevapi.entities;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents permission level for a user.
 *
 * @version 04.04.2022
 */
@Entity
@Table(name = "permission_level")
public class PermissionLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, name = "pl_id")
    private long id;

    @ApiModelProperty("The roles of the permission levels")
    @Column(unique = true, name = "adminType")
    private String adminType;


    @ApiModelProperty("When the product was created.")
    @Column(name = "created_at", columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    @ApiModelProperty("When the product was last updated.")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ApiModelProperty("The users assigned to this permission level")
    @OneToMany(mappedBy = "permissionLevel")
    private List<User> users;

    /**
     * Creates an instance of permission level.
     *
     * @param adminType the type of role of this permission level.
     * @param updatedAt when this permission level was last updated.
     */
    public PermissionLevel(String adminType,LocalDateTime updatedAt) {
        this.adminType = adminType;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = updatedAt;
    }

    public PermissionLevel() {};


    /**
     * Returns the id of this permission level.
     *
     * @return id of this permission level.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of this permission level.
     *
     * @param id the id to be set for this permission level.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the type of role for this permission level.
     *
     * @return the type of role for this permission level.
     */
    public String getAdminType() {
        return adminType;
    }

    /**
     * Sets the type of role for this permission level.
     *
     * @param adminType the type of role to set this permission level to.
     */
    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }



    /**
     * Returns the date and time the premission level was created.
     *
     * @return the date and time the premission was created.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     *
     *
     * @param createdAt
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the date this permission level was last updated.
     *
     * @return the date this permission level was last updated.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the date this permission level was last updated.
     *
     * @param updatedAt sets the date this permission level was last updated.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
