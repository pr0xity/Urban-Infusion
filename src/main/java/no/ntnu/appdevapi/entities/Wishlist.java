package no.ntnu.appdevapi.entities;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a wishlist owned by a user.
 */
@Entity
@Table(name = "fav_list")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fl_id")
    private long id;

    @ApiModelProperty("The user of the wishlist")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @ApiModelProperty("Set of products in the wishlist")
    @ManyToMany
    @JoinTable(name = "fav_item",
            joinColumns = @JoinColumn(name="fl_id"),
            inverseJoinColumns = @JoinColumn(name="product_id")
    )
    private Set<Product> products = new HashSet<>();

    @ApiModelProperty("When the wishlist was last updated.")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ApiModelProperty("When the wishlist was last updated.")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Creates an instance of wishlist.
     *
     * @param user the user who owns this wishlist.
     */
    public Wishlist(User user) {
        this.user = user;
        this.user.setWishlist(this);
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Empty constructor.
     */
    public Wishlist() {

    }

    /**
     * Returns the id of this wishlist.
     *
     * @return id of this wishlist.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of this wishlist.
     *
     * @param id the id to be set on this wishlist.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user who owns this wishlist.
     *
     * @return user who owns this wishlist
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who owns this wishlist.
     *
     * @param user the user to be set for this wishlist.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the products in this wishlist.
     *
     * @return products in this wishlist.
     */
    public Set<Product> getProducts() {
        return products;
    }

    /**
     * Sets the set of products in this wishlist.
     *
     * @param products set of products to be set.
     */
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    /**
     * Adds a product to this wishlist.
     *
     * @param product the product to be added to this wishlist.
     */
    public void addProduct(Product product) {
        this.products.add(product);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Deletes the product from this wishlist.
     *
     * @param product product to be deleted from this wishlist.
     */
    public void deleteProduct(Product product) {
        this.products.remove(product);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Returns when this wishlist was last updated.
     *
     * @return when this wishlist was last updated.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets when this wishlist was last updated to now.
     */
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Returns when this wishlist was created.
     *
     * @return when this wishlist was created.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets when this wishlist was created to now.
     */
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}
