package no.ntnu.appdevapi.entities;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a shopping session on an online store.
 */
@Entity
@Table(name = "shopping_sessions")
public class ShoppingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ApiModelProperty("The id of the user of this shopping session.")
    private int userId;

    @ApiModelProperty("The total cost of the items in this shopping session.")
    private double total;

    @ApiModelProperty("When the shopping session was created.")
    private LocalDateTime createdAt;

    @ApiModelProperty("When the shopping session was last updated.")
    private LocalDateTime updatedAt;

    /**
     * Creates an instance of shopping session.
     *
     * @param id id of this shopping session.
     * @param userId user id of this shopping session.
     * @param total the total cost of this shopping session.
     * @param createdAt when this shopping sessions was created
     * @param updatedAt when this shopping session was last updated.
     */
    public ShoppingSession(int id, int userId, double total, LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.total = total;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ShoppingSession() {

    }

    /**
     * Returns the id of this shopping session.
     *
     * @return id of this shopping session.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of this shopping session.
     *
     * @param id the id to be set for this shopping session.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user id of this shopping session.
     *
     * @return user id of this shopping session.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id for this shopping session.
     *
     * @param userId the user id to be set for this shopping session.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the total cost of this shopping session.
     *
     * @return total cost of this shopping session.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total cost of this shopping session.
     *
     * @param total the new total cost of this shopping session.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Returns when this shopping sessions was created.
     *
     * @return when this shopping session was created.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets when this shopping session was created.
     *
     * @param createdAt the date to be set for when this shopping session was created.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns when this shopping session was last updated.
     *
     * @return when this shopping session was last updated.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets when this shopping session was last updated.
     *
     * @param updatedAt the date to be set for when this shopping session was last updated.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
