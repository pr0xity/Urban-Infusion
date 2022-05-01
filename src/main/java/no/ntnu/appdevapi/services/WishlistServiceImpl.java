package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.WishlistRepository;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for wishlist.
 */
@Service(value = "wishlistService")
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    /**
     * Returns a list over all wishlists.
     *
     * @return list over all wishlists.
     */
    public List<Wishlist> getAllWishlists() {
        List<Wishlist> wishlists = new ArrayList<>();
        wishlistRepository.findAll().forEach(wishlists::add);
        return wishlists;
    }

    /**
     * Returns the wishlist with the given id.
     *
     * @param id id of the wishlist to find.
     * @return the wishlist with the given id or null if not found.
     */
    public Wishlist getWishlist(long id) {
        Optional<Wishlist> wishlist = wishlistRepository.findById(id);
        return wishlist.orElse(null);
    }

    /**
     * Returns the wishlist of the given user, null if not found.
     *
     * @param user the user to find the wishlist of.
     * @return wishlist of the given user, null if none.
     */
    public Wishlist getWishlistByUser(User user) {
        return this.wishlistRepository.findByUser(user).orElse(null);
    }

    /**
     * Adds a wishlist.
     *
     * @param wishlist the wishlist to be added.
     */
    public void addWishlist(Wishlist wishlist) {
        if (this.wishlistRepository.findById(wishlist.getId()).isEmpty()) {
            this.wishlistRepository.save(wishlist);
        }
    }

    public void addProductToWishlist(Wishlist wishlist, Product product) {
        wishlist.addProduct(product);
        updateWishlist(wishlist.getId(), wishlist);
    }

    public void deleteProductFromWishlist(Wishlist wishlist, Product product) {
        wishlist.deleteProduct(product);
        updateWishlist(wishlist.getId(), wishlist);
    }

    /**
     * Updates the wishlist with the given id.
     *
     * @param id the id of the wishlist to update.
     * @param wishlist the wishlist to update to.
     */
    public void updateWishlist(long id, Wishlist wishlist) {
        if (wishlist != null && wishlist.getId() == id && getWishlist(id) != null) {
            this.wishlistRepository.save(wishlist);
        }
    }

    /**
     * Deletes the wishlist with the given id.
     *
     * @param id id of the wishlist to be deleted.
     */
    public void deleteWishlist(long id) {
        this.wishlistRepository.deleteById(id);
    }
}
