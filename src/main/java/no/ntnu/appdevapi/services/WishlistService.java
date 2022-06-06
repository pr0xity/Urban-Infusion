package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.User;
import no.ntnu.appdevapi.entities.Wishlist;

import java.util.List;

/**
 * Business logic for wishlist.
 */
public interface WishlistService {

  /**
   * Returns a list over all wishlists.
   *
   * @return list over all wishlists.
   */
  List<Wishlist> getAllWishlists();

  /**
   * Returns the wishlist with the given id.
   *
   * @param id id of the wishlist to find.
   * @return the wishlist with the given id or null if not found.
   */
  Wishlist getWishlist(long id);

  /**
   * Returns the wishlist of the given user, null if not found.
   *
   * @param user the user to find the wishlist of.
   * @return wishlist of the given user, null if none.
   */
  Wishlist getWishlistByUser(User user);

  /**
   * Returns the wishlist which has the given sharing token.
   *
   * @param sharingToken the token to find wishlist for.
   * @return wishlist with the given sharing token, null if not found.
   */
  Wishlist getWishlistBySharingToken(String sharingToken);

  /**
   * Adds a wishlist.
   *
   * @param wishlist the wishlist to be added.
   */
  void addWishlist(Wishlist wishlist);

  /**
   * Adds the given product to the wishlist.
   *
   * @param wishlist wishlist to add product to.
   * @param product product to add to wishlist.
   */
  void addProductToWishlist(Wishlist wishlist, Product product);

  /**
   * Deletes the given product from the wishlist.
   *
   * @param wishlist wishlist to delete product from.
   * @param product product to delete from wishlist.
   */
  void deleteProductFromWishlist(Wishlist wishlist, Product product);

  /**
   * Updates the wishlist with the given id.
   *
   * @param id the id of the wishlist to update.
   * @param wishlist the wishlist to update to.
   */
  void updateWishlist(long id, Wishlist wishlist);

  /**
   * Deletes the wishlist with the given id.
   *
   * @param id id of the wishlist to be deleted.
   */
  void deleteWishlist(long id);
}
