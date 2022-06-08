package no.ntnu.appdevapi.entities;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Represents an image of a product.
 */
@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, name = "product_image_id")
    private long id;

    @Lob
    private byte [] data;

    @ApiModelProperty("The file extension if the image (.jpg, .png etc.)")
    private String fileExtension;

    @ApiModelProperty("The content type of the image (image/jpeg, image/png etc.)")
    private String contentType;

    /**
     * Creates an instance of product image.
     *
     * @param data they array of bytes of an image.
     * @param extension the file extension of this image (.jpg, png, etc.)
     * @param contentType the content type of this image (image/jpeg, image/png, .etc)
     */
    public ProductImage(byte[] data, String extension, String contentType) {
        this.data = data;
        this.fileExtension = extension;
        this.contentType = contentType;
    }

    /**
     * Empty constructor.
     */
    public ProductImage() {

    }

    /**
     * Returns this product image's id.
     *
     * @return this product image's id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of this product image.
     *
     * @param id the id to be set for this product image.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the array of bytes making up this image.
     *
     * @return the array of bytes making up this image.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the array of bytes making up this image.
     *
     * @param data the new array of bytes.
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Returns the file extension of this image.
     *
     * @return the file extension of this image.
     */
    public String getFileExtension() {
        return fileExtension;
    }

    /**
     * Sets the file extension of this image.
     *
     * @param fileExtension file extension to be set for this image.
     */
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * Returns the content-type of this image.
     *
     * @return content-type of this image.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content-type of this image.
     *
     * @param contentType content-type of this image.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
