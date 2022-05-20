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

    @ApiModelProperty("The product this image belongs to.")
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id")
    private Product product;

    @ApiModelProperty("The file extension if the image (.jpg, .png etc.)")
    private String fileExtension;

    @ApiModelProperty("")
    private String contentType;

    /**
     * Creates an instance of product image.
     *
     * @param data they array of bytes of an image.
     * @param product the product this image belongs to.
     * @param extension the file extension of this image (.jpg, png, etc.)
     * @param contentType the content type of this image (image/jpeg, image/png, .etc)
     */
    public ProductImage(byte[] data, Product product, String extension, String contentType) {
        this.data = data;
        this.product = product;
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
     * Returns the product belonging to this image.
     *
     * @return returns the product belonging to this image.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product belonging to this image.
     *
     * @param product the product to be set to this image.
     */
    public void setProduct(Product product) {
        this.product = product;
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
     * @return content-tupe fo this image.
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
