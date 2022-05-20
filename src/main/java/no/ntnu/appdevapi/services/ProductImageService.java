package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ProductImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * Business logic for product images.
 */
public interface ProductImageService {

    /**
     * Adds the given image file to the given product.
     *
     * @param imageFile the image file to add for the product.
     * @param product the product to add the image for.
     * @return the image added.
     */
    ProductImage addImage(MultipartFile imageFile, Product product);

    /**
     * Finds and returns the image containing the given id.
     *
     * @param id the id of the image to find.
     * @return the image with the given id, null if not found.
     */
    ProductImage getImageById(long id);

    /**
     * Finds and returns the image belonging to the given product.
     *
     * @param product the product to find image of.
     * @return the image belonging to the given product, null if not found.
     */
    ProductImage getImageByProduct(Product product);

    /**
     * Updates the product image for the product with the given id.
     *
     * @param productId the id of the product to update image for.
     * @param imageFile the image to update with.
     * @return the image that was updated.
     */
    ProductImage updateImage(long productId, MultipartFile imageFile);


}
