package com.example.dt_2.service;

import com.example.dt_2.model.Product;
import com.example.dt_2.model.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Optional<Product> save(String name);

    Optional<Product> save(ProductDTO productDto);

    Optional<Product> edit(Long id, String name);

    Optional<Product> edit(Long id, ProductDTO productDto);

    void deleteById(Long id);

}
