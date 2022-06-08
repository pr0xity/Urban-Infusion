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
@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductService productService;

    /**
     * Gets a list of all wish lists in the database.
     *
     * @return {@code List<Wishlist>} of all wishLists.
     */
    @Override
    public List<Wishlist> getAllWishlists() {
        List<Wishlist> wishlists = new ArrayList<>();
        wishlistRepository.findAll().forEach(wishlists::add);
        return wishlists;
    }

    /**
     * Gets the wish list with the given id.
     *
     * @param id id of the wishlist to find.
     * @return {@code Wishlist} with given ID, or null if no match.
     */
    @Override
    public Wishlist getWishlist(long id) {
        Optional<Wishlist> wishlist = wishlistRepository.findById(id);
        return wishlist.orElse(null);
    }

    /**
     * Gets the wish list of the given user.
     *
     * @param user the user to find the wishlist of.
     * @return {@code Wishlist} belonging to given user.
     */
    @Override
    public Wishlist getWishlistByUser(User user) {
        return this.wishlistRepository.findByUser(user).orElse(null);
    }

    /**
     * Gets the wish list by given sharing token.
     *
     * @param sharingToken the token to find wishlist for.
     * @return {@code Wishlist} matching given sharingToken, or null if no match.
     */
    @Override
    public Wishlist getWishlistBySharingToken(String sharingToken) {
        return this.wishlistRepository.findBySharingToken(sharingToken).orElse(null);
    }

    /**
     * Adds given wish list to the database.
     *
     * @param wishlist the wishlist to be added.
     */
    @Override
    public void addWishlist(Wishlist wishlist) {
        if (this.wishlistRepository.findById(wishlist.getId()).isEmpty()) {
            this.wishlistRepository.save(wishlist);
        }
    }

    /**
     * Adds given product to given wish list.
     *
     * @param wishlist wishlist to add product to.
     * @param product product to add to wishlist.
     */
    @Override
    public void addProductToWishlist(Wishlist wishlist, Product product) {
        wishlist.addProduct(productService.getProduct(product.getId()));
        updateWishlist(wishlist.getId(), wishlist);
    }

    /**
     * Deletes given product from given wish list.
     *
     * @param wishlist wishlist to delete product from.
     * @param product product to delete from wish list.
     */
    @Override
    public void deleteProductFromWishlist(Wishlist wishlist, Product product) {
        wishlist.deleteProduct(product);
        updateWishlist(wishlist.getId(), wishlist);
    }

    /**
     * Updates the wish list with the given id.
     *
     * @param id the id of the wishlist to update.
     * @param wishlist the wishlist to update to.
     */
    @Override
    public void updateWishlist(long id, Wishlist wishlist) {
        if (wishlist != null && wishlist.getId() == id && getWishlist(id) != null) {
            wishlist.setUpdatedAt();
            this.wishlistRepository.save(wishlist);
        }
    }

    /**
     * Deletes the wish list with the given id from the database.
     * @param id id of the wishlist to be deleted.
     */
    @Override
    public void deleteWishlist(long id) {
        this.wishlistRepository.deleteById(id);
    }
}
