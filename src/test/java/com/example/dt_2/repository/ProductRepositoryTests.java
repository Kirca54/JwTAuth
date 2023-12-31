package com.example.dt_2.repository;

import com.example.dt_2.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    private Product savedProduct;
    private int size;

    @BeforeEach
    public void setup() {
        // get the size
        List<Product> allProducts = productRepository.findAll();
        size = allProducts.size();

        // Create a sample product before each test
        Product product = new Product();
        product.setName("TestProduct");
        savedProduct = productRepository.save(product);
    }

    @Test
    @Transactional
    public void testFindAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        assertEquals(size + 1, allProducts.size());
        assertTrue(allProducts.stream().anyMatch(product -> product.getName().equals("TestProduct")));
    }

    @Test
    public void testFindById() {
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals(savedProduct.getName(), foundProduct.get().getName());
    }

    @Test
    public void testSaveProduct() {
        Product newProduct = new Product();
        newProduct.setName("AnotherProduct");
        Product savedProduct = productRepository.save(newProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("AnotherProduct", savedProduct.getName());
    }

    @Test
    public void testUpdateProduct() {
        savedProduct.setName("UpdatedProduct");
        Product updatedProduct = productRepository.save(savedProduct);
        assertEquals(savedProduct.getId(), updatedProduct.getId());
        assertEquals("UpdatedProduct", updatedProduct.getName());
    }

    @Test
    public void testDeleteProduct() {
        productRepository.delete(savedProduct);
        assertFalse(productRepository.findById(savedProduct.getId()).isPresent());
    }

    @Test
    public void testFindByName() {
        Optional<Product> foundProduct = productRepository.findByName("TestProduct");
        assertTrue(foundProduct.isPresent());
        assertEquals("TestProduct", foundProduct.get().getName());
    }
}
