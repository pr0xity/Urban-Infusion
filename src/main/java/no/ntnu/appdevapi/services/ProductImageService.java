package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.ProductImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * Business logic for product images.
 */
public interface ProductImageService {

    /**
     * Adds the given image file.
     *
     * @param imageFile the image file to add.
     * @return the product image added.
     */
    ProductImage addImage(MultipartFile imageFile);

    /**
     * Finds and returns the image containing the given id.
     *
     * @param id the id of the image to find.
     * @return the image with the given id, null if not found.
     */
    ProductImage getImageById(long id);

    /**
     * Updates the product image with the given id to the given image file.
     *
     * @param imageId id of the product image to update.
     * @param imageFile the image file to update the product image to.
     * @return the updated product image.
     */
    ProductImage updateImage(long imageId, MultipartFile imageFile);
}
