package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.RatingRepository;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for ratings.
 */
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Returns a list over all ratings.
     *
     * @return list over all ratings.
     */
    public List<Rating> getAllRatings() {
        List<Rating> ratings = new ArrayList<>();
        ratingRepository.findAll().forEach(ratings::add);
        return ratings;
    }

    /**
     * Returns the rating containing the specified product id.
     *
     * @param product the product id to retrieve ratings for.
     * @return returns the rating for the product with the given product id.
     */
    public List<Rating> getRatingsFromProduct(Product product) {
        return new ArrayList<>(ratingRepository.findByProduct(product));
    }

    /**
     * Returns the rating with the given id.
     *
     * @param id id of the rating to find.
     * @return the rating with the given id or null if not found.
     */
    public Rating getRating(long id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        return rating.orElse(null);
    }

    /**
     * Adds a rating.
     *
     * @param rating the rating to be added.
     */
    public void addRating(Rating rating) {
        ratingRepository.save(rating);
    }

    /**
     * Updates the rating with the given id.
     *
     * @param id the id of the rating to update.
     * @param rating the rating to update to.
     */
    public void updateRating(long id, Rating rating) {
        if (rating != null && rating.getId() == id && getRating(id) != null) {
            this.ratingRepository.save(rating);
        }
    }

    /**
     * Deletes the rating with the given id.
     *
     * @param id id of the rating to be deleted.
     */
    public void deleteRating(long id) {
        this.ratingRepository.deleteById(id);
    }
}
