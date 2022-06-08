package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.ProductCategoryRepository;
import no.ntnu.appdevapi.DTO.ProductDto;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.DAO.ProductRepository;
import no.ntnu.appdevapi.entities.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    /**
     * Gets a list of all products sorted by ID.
     *
     * @return {@code List<Product>} of all products sorted by ID.
     */
    public Iterable<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);

        return products.stream().sorted(Comparator.comparingLong(Product::getId)).collect(Collectors.toList());
    }

    /**
     * Gets a list of all currently active products.
     *
     * @return {@code List<Product>} of currently active products.
     */
    public Iterable<Product> getAllProductsNotDeleted() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);

        return products.stream().sorted(Comparator.comparingLong(Product::getId)).filter(product -> !product.isInactive()).collect(Collectors.toList());
    }

    /**
     * Gets a single product by ID.
     *
     * @param id the ID of the product.
     * @return {@code Product} with the given ID.
     */
    public Product getProduct(long id) {
        Optional<Product> p = productRepository.findById(id);
        return p.orElse(null);
    }

    /**
     * Gets a single product by name.
     *
     * @param name the name of the product.
     * @return {@code Product} with the given name.
     */
    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }

    /**
     * Adds a product to the database.
     *
     * @param product the product as a {@code ProductDTO}.
     * @return {@code Product} that was added.
     */
    public Product addProductFromDto(ProductDto product) {
        Product nProduct = getProductFromDto(product);
        if (productRepository.findByName(nProduct.getName()) == null){
            ProductCategory category = productCategoryRepository.findByName(nProduct.getCategory().getName());
            if (category == null){
                productCategoryRepository.save(nProduct.getCategory());
                nProduct.setCategory(productCategoryRepository.findByName(nProduct.getCategory().getName()));
            } else {
                nProduct.setCategory(category);
            }
            productRepository.save(nProduct);
        }
        return productRepository.findByName(nProduct.getName());
    }

    /**
     * Updates the product with the given ID from DTO.
     *
     * @param id the ID of the product.
     * @param product the updated product as {@code ProductDTO}
     * @return the updated {@code Product}, or null if error.
     */
    public Product updateProduct(long id, ProductDto product) {
        Product newProduct = getProductFromDto(product);
        Product old = productRepository.findById(id).orElse(null);

        if (null == old) {
            return null;
        }
        ProductCategory oldCategory = old.getCategory();
        boolean changedCat = false;

        if (null != newProduct.getName()) {
            old.setName(newProduct.getName());
        }
        if (null != newProduct.getCategory()) {
            ProductCategory dbCat = productCategoryRepository.findByName(newProduct.getCategory().getName());
            old.setCategory(Objects.requireNonNullElseGet(dbCat, () -> productCategoryRepository.save(newProduct.getCategory())));
            changedCat = true;
        }
        if (0 != newProduct.getPrice()) {
            old.setPrice(newProduct.getPrice());
        }
        if (null != newProduct.getDescription()) {
            old.setDescription(newProduct.getDescription());
        }
        old.setUpdatedAt(LocalDateTime.now());

        if (null != newProduct.getDeletedAt()) {
            old.setDeletedAt(newProduct.getDeletedAt());
        }
        if (null != newProduct.getOrigin()) {
            old.setOrigin(newProduct.getOrigin());
        }
        if (0 != newProduct.getInventory()) {
            old.setInventory(newProduct.getInventory());
        }
        if (newProduct.getImageId() != 0) {
            old.setImageId(newProduct.getImageId());
        }
        old.setInactive(newProduct.isInactive());

        productRepository.save(old);
        if (changedCat) {
            List<Product> catProducts = new ArrayList<>();
            productRepository.findAll().forEach(p -> {
                if (p.getCategory().getName().equals(oldCategory.getName())) {
                    catProducts.add(p);
                }
            });
            if (catProducts.isEmpty()) {
                productCategoryRepository.delete(oldCategory);
            }
        }
        return productRepository.findById(old.getId()).orElse(null);
    }

    /**
     * Updates a product with the given id.
     *
     * @param productId ID of the product to update.
     * @param product the product object to update to.
     * @return the updated {@code Product}, or null if error.
     */
    @Override
    public Product updateProductWithProductObject(long productId, Product product) {
        Product existingProduct = productRepository.findById(productId).orElse(null);
        if (existingProduct != null && product.getId() == productId) {
            return productRepository.save(product);
        }
        return null;
    }

    /**
     * Deletes the product with the given ID.
     *
     * @param id the ID of the product.
     */
    public void deleteProduct(long id) {
        Product product = getProduct(id);
        if (product != null) {
            product.setDeletedAt(LocalDateTime.now());
            product.setInactive(true);
            productRepository.save(product);
        }
    }

    /**
     * Converts a ProductDTO object to a Product object.
     *
     * @param object the object to be converted.
     * @return the converted {@code Product}.
     */
    private Product getProductFromDto(ProductDto object) {
        Product product = new Product();
        product.setName(object.getName());
        product.setDescription(object.getDescription());
        product.setOrigin(object.getOrigin());
        product.setPrice(object.getPrice());
        product.setInventory(object.getInventory());
        product.setCreatedAt(object.getCreatedAt());
        product.setUpdatedAt(object.getUpdatedAt());
        product.setImageId(object.getImageId());
        product.setInactive(object.isInactive());

        if (null != object.getCategory()) {
            String[] cName = object.getCategory().split(" ");
            StringBuilder categoryName = new StringBuilder();
            for (int i = 0; i < cName.length; i++) {
                categoryName.append(cName[i].substring(0, 1).toUpperCase(Locale.ROOT));
                categoryName.append(cName[i].substring(1).toLowerCase(Locale.ROOT));
                if (i < cName.length - 1) {
                    categoryName.append(" ");
                }
            }
            ProductCategory c = new ProductCategory(categoryName.toString());

            product.setCategory(c);
        }
        return product;
    }
}
