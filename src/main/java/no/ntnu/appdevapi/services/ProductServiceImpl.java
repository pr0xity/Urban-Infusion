package no.ntnu.appdevapi.services;

import no.ntnu.appdevapi.DAO.ProductCategoryRepository;
import no.ntnu.appdevapi.DTO.ProductDto;
import no.ntnu.appdevapi.entities.Product;
import no.ntnu.appdevapi.DAO.ProductRepository;
import no.ntnu.appdevapi.entities.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}