package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.Wishlist;

import java.util.List;

public interface WishlistService {
  List<Wishlist> getAllWishlists();

  Wishlist getWishlist(long id);

  Wishlist getWishlistByUser(User user);

  void addWishlist(Wishlist wishlist);

  void addProductToWishlist(Wishlist wishlist, Product product);

  void deleteProductFromWishlist(Wishlist wishlist, Product product);

  void updateWishlist(long id, Wishlist wishlist);

  void deleteWishlist(long id);
}
