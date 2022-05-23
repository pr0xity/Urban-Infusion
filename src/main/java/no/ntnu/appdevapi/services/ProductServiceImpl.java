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

    public Iterable<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products.stream().filter(product -> product.getDeletedAt() == null).collect(Collectors.toList());
    }

    public Product getProduct(long id) {
        Optional<Product> p = productRepository.findById(id);
        return p.orElse(null);
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }


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

    public Product updateProduct(long id, ProductDto product) {
        Product newProduct = getProductFromDto(product);

        Product old = productRepository.findById(id).orElse(null);
        if (null == old) {
            return null;
        }
        if (null != newProduct.getName()) {
            old.setName(newProduct.getName());
        }
        if (null != newProduct.getCategory()) {
            ProductCategory dbCat = productCategoryRepository.findByName(newProduct.getCategory().getName());
            old.setCategory(Objects.requireNonNullElseGet(dbCat, () -> productCategoryRepository.save(newProduct.getCategory())));
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
        Product product = getProduct(id);
        if (product != null) {
            product.setDeletedAt(LocalDateTime.now());
            productRepository.save(product);
        }
    }

    private Product getProductFromDto(ProductDto object) {
        Product product = new Product();
        product.setName(object.getName());
        product.setDescription(object.getDescription());
        product.setOrigin(object.getOrigin());
        product.setPrice(object.getPrice());
        product.setInventoryId(object.getInventoryId());
        product.setCreatedAt(object.getCreatedAt());
        product.setUpdatedAt(object.getUpdatedAt());

        if (null != object.getCategoryName()) {
            String[] cName = object.getCategoryName().split(" ");
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
