package no.ntnu.appdevapi.entities;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a rating made by a user.
 */
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ApiModelProperty("The the user of who made the rating.")
    @OneToOne
    private User user;

    @ApiModelProperty("The product id of the product rated.")
    @ManyToOne
    private Product product;

    @ApiModelProperty("Integer value of this rating.")
    private int rating;

    @ApiModelProperty("Contains the comment for this rating.")
    private String comment;

    @ApiModelProperty("When the rating was last updated.")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Creates an instance of a rating
     *
     * @param user the user who made this rating.
     * @param product the product this rating belongs to.
     * @param rating the integer value of this rating.
     * @param comment the comment of this rating.
     */
    public Rating(User user, Product product, int rating, String comment) {
        this.user = user;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Empty constructor.
     */
    public Rating() {

    }

    /**
     * Returns the id of this rating.
     *
     * @return the id of this rating.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the user who made this rating.
     *
     * @return user who made this rating.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who made this rating.
     *
     * @param user  the user who made this rating.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the product this rating was made for.
     *
     * @return product this rating was made for.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product this rating was made for.
     *
     * @param product the product this rating was made for.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Returns the value of this rating.
     *
     * @return value of this rating.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the value of rating.
     *
     * @param rating the value of the rating.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Returns the comment of this rating.
     *
     * @return comment of this rating.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment of this rating.
     *
     * @param comment comment of this rating.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns when this rating was last updated.
     *
     * @return when this rating was last updated.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets when this rating was last updated.
     *
     * @param updatedAt the new date for when this rating was updated.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
