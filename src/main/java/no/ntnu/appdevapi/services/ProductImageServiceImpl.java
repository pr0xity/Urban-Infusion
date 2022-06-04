package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.ProductImageRepository;
import no.ntnu.appdevapi.DAO.ProductRepository;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.entities.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    private final String[] FILE_EXTENSIONS = {"jpeg", "jpg", "png", "webp", "svg"};
    private final String[] CONTENT_TYPES = {"image/jpeg", "image/jpg", "image/png",  "image/webp", "image/svg+xml"};

    @Override
    public ProductImage addImage(MultipartFile imageFile, Product product) {
        ProductImage productImage = null;
        try {
            if (isImageFileValid(imageFile)) {
                byte[] data = imageFile.getBytes();
                String fileExtension = getFileExtension(imageFile);
                String contentType = imageFile.getContentType();
                productImage = productImageRepository.save(new ProductImage(data, product, fileExtension, contentType));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productImage;
    }

    @Override
    public ProductImage addProductImage(ProductImage imageFile, Product product) {
        ProductImage existingImage = getImageByProduct(product);
        if(existingImage == null){
            productImageRepository.save(imageFile);
        }
        return imageFile;
    }

    @Override
    public ProductImage getImageById(long id) {
        return productImageRepository.findById(id).orElse(null);
    }

    @Override
    public ProductImage getImageByProduct(Product product) {
        return productImageRepository.findByProduct(product);
    }

    @Override
    public ProductImage updateImage(long productId, ProductImage imageFile) {
        ProductImage existingImage = getImageByProduct(productRepository.findById(productId).orElse(null));
        if (existingImage != null) {
            existingImage.setData(imageFile.getData());
            existingImage.setContentType(imageFile.getContentType());
            existingImage.setFileExtension(imageFile.getFileExtension());
            productImageRepository.save(existingImage);
        }

        return existingImage;
    }

    /**
     * Finds and returns the file extension of the given image file.
     *
     * @param imageFile the image file to find file extension of.
     * @return the file extension of the given image file.
     */
    private String getFileExtension(MultipartFile imageFile) {
        String filename = imageFile.getOriginalFilename();
        String fileExtension = "";
        if (filename != null) {
            fileExtension =  filename.split("\\.", 2)[1];
        }
        return fileExtension;
    }

    /**
     * Checks if the given file extension is valid.
     *
     * @param fileExtension file extension to check.
     * @return true if valid, false if not.
     */
    private boolean isFileExtensionValid(String fileExtension) {
        return fileExtension != null && Arrays.asList(FILE_EXTENSIONS).contains(fileExtension);
    }

    /**
     * Checks if the given content type is valid.
     *
     * @param contentType content type to check.
     * @return true if valid, false if not.
     */
    private boolean isContentTypeValid(String contentType) {
        return contentType != null && Arrays.asList(CONTENT_TYPES).contains(contentType);
    }

    /**
     * Checks if the given image file is valid.
     *
     * @param imageFile image file to check.
     * @return true if valid, false if not.
     */
    private boolean isImageFileValid(MultipartFile imageFile) {
        String fileExtension = getFileExtension(imageFile);
        String contentType = imageFile.getContentType();
        return isFileExtensionValid(fileExtension) && isContentTypeValid(contentType);
    }
}
