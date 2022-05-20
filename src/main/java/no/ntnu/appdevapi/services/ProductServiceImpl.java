package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.ProductCategoryRepository;
import no.ntnu.appdevapi.DTO.ProductDto;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.DAO.ProductRepository;
import no.ntnu.appdevapi.entities.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(long id) {
        Optional<Product> p = productRepository.findById(id);
        return p.orElse(null);
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }


    public Product addProductFromDto(ProductDto product) {
        Product nProduct = product.getProductFromDto();
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

    public Product updateProduct(ProductDto product) {
        Product newProduct = product.getProductFromDto();

        Product old = productRepository.findById(newProduct.getId()).orElse(null);
        if (null == old) {
            return null;
        }
        if (null != newProduct.getName()) {
            old.setName(newProduct.getName());
        }
        if (null != newProduct.getCategory()) {
            old.setCategory(productCategoryRepository.findByName(newProduct.getCategory().getName()));
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
        if (0 != newProduct.getInventoryId()) {
            old.setInventoryId(newProduct.getInventoryId());
        }

        productRepository.save(old);

        return productRepository.findById(old.getId()).orElse(null);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
