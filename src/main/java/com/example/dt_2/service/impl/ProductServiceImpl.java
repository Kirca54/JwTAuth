package com.example.dt_2.service.impl;

import com.example.dt_2.model.Product;
import com.example.dt_2.model.ProductDTO;
import com.example.dt_2.repository.ProductRepository;
import com.example.dt_2.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Optional<Product> save(String name) {
        Product newProduct = new Product(name);
        return Optional.of(productRepository.save(newProduct));
    }

    @Override
    public Optional<Product> save(ProductDTO productDto) {
        Product newProduct = new Product(productDto.getName());
        return Optional.of(productRepository.save(newProduct));
    }

    @Override
    public Optional<Product> edit(Long id, String name) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("No such element exists with id: " + id));
        product.setName(name);
        return Optional.of(productRepository.save(product));
    }

    @Override
    public Optional<Product> edit(Long id, ProductDTO productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("No such element exists with id: " + id));
        product.setName(productDto.getName());
        return Optional.of(productRepository.save(product));
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("No such element exists with id: " + id));
        productRepository.delete(product);
    }
}
