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

    @Override
    public List<Wishlist> getAllWishlists() {
        List<Wishlist> wishlists = new ArrayList<>();
        wishlistRepository.findAll().forEach(wishlists::add);
        return wishlists;
    }

    @Override
    public Wishlist getWishlist(long id) {
        Optional<Wishlist> wishlist = wishlistRepository.findById(id);
        return wishlist.orElse(null);
    }

    @Override
    public Wishlist getWishlistByUser(User user) {
        return this.wishlistRepository.findByUser(user).orElse(null);
    }

    @Override
    public Wishlist getWishlistBySharingToken(String sharingToken) {
        return this.wishlistRepository.findBySharingToken(sharingToken).orElse(null);
    }

    @Override
    public void addWishlist(Wishlist wishlist) {
        if (this.wishlistRepository.findById(wishlist.getId()).isEmpty()) {
            this.wishlistRepository.save(wishlist);
        }
    }

    @Override
    public void addProductToWishlist(Wishlist wishlist, Product product) {
        wishlist.addProduct(productService.getProduct(product.getId()));
        updateWishlist(wishlist.getId(), wishlist);
    }

    @Override
    public void deleteProductFromWishlist(Wishlist wishlist, Product product) {
        wishlist.deleteProduct(product);
        updateWishlist(wishlist.getId(), wishlist);
    }

    @Override
    public void updateWishlist(long id, Wishlist wishlist) {
        if (wishlist != null && wishlist.getId() == id && getWishlist(id) != null) {
            wishlist.setUpdatedAt();
            this.wishlistRepository.save(wishlist);
        }
    }

    @Override
    public void deleteWishlist(long id) {
        this.wishlistRepository.deleteById(id);
    }
}
