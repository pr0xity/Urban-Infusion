package no.ntnu.appdevapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(int index) {
        Optional<Product> p = productRepository.findById(index);
        return p.orElse(null);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(int index) {
        productRepository.deleteById(index);
    }
}
