package com.example.dt_2.service;

import com.example.dt_2.model.Product;
import com.example.dt_2.model.ProductDTO;
import com.example.dt_2.repository.ProductRepository;
import com.example.dt_2.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    private List<Product> products;

    @BeforeEach
    public void setUp() {
        products = new ArrayList<>();
        products.add(new Product("productName1"));
        products.add(new Product("productName2"));
        products.add(new Product("productName3"));

        for (int i = 0; i < products.size(); i++) {
            products.get(i).setId((long) (i + 1));
        }
    }

    @Test
    public void getAllProductsTest() {
        //when
        when(productRepository.findAll()).thenReturn(products);

        //test
        List<Product> proList = productService.findAll();
        assertEquals(3, proList.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getProductByIdTest() {
        // given
        Product product = products.get(0);

        //when
        when(productRepository.findById(product.getId())).thenReturn(java.util.Optional.of(product));

        //test
        assertEquals(product.getName(), productService.findById(product.getId()).get().getName());
    }

    @Test
    public void createProductWithSeparateParametersTest() {
        // given
        Product product = products.get(0);

        //when
        when(productRepository.save(any(Product.class))).thenReturn(product);

        //test
        Optional<Product> createdProduct = productService.save(product.getName());
        assertFalse(createdProduct.isEmpty());
        assertEquals(product.getName(), createdProduct.get().getName());
    }

    @Test
    public void createProductWithDTO() {
        // given
        ProductDTO productDTO = new ProductDTO("newProductName");

        // Mock the repository behavior for save
        when(productRepository.save(any(Product.class))).thenReturn(new Product(productDTO.getName()));

        // when
        Optional<Product> createdProduct = productService.save(productDTO);

        // then
        verify(productRepository).save(any(Product.class));

        assertTrue(createdProduct.isPresent());
        assertEquals(productDTO.getName(), createdProduct.get().getName());
    }

    @Test
    public void editProductByIdWithSeparateParametersTest() {
        // given
        Product originalProduct = products.get(0);
        String newName = "updatedProductName";

        // Mock the repository behavior for findById and save
        when(productRepository.findById(originalProduct.getId())).thenReturn(Optional.of(originalProduct));
        when(productRepository.save(any(Product.class))).thenReturn(new Product(newName));

        // when
        Optional<Product> editedProduct = productService.edit(originalProduct.getId(), newName);

        // then
        verify(productRepository).findById(originalProduct.getId());
        verify(productRepository).save(any(Product.class));

        assertTrue(editedProduct.isPresent());
        assertEquals(newName, editedProduct.get().getName());
    }

    @Test
    public void editProductByIdWithDTOTest() {
        // given
        Product originalProduct = products.get(0);
        ProductDTO productDTO = new ProductDTO("updatedProductName");

        // Mock the repository behavior for findById and save
        when(productRepository.findById(originalProduct.getId())).thenReturn(Optional.of(originalProduct));
        when(productRepository.save(any(Product.class))).thenReturn(new Product(productDTO.getName()));

        // when
        Optional<Product> editedProduct = productService.edit(originalProduct.getId(), productDTO);

        // then
        verify(productRepository).findById(originalProduct.getId());
        verify(productRepository).save(any(Product.class));

        assertTrue(editedProduct.isPresent());
        assertEquals(productDTO.getName(), editedProduct.get().getName());
    }

    @Test
    public void deleteProductByIdTest() {
        // given
        Product product = products.get(0);

        // Mock the repository behavior for findById and delete
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(Product.class));

        // when
        productService.deleteById(product.getId());

        // then
        verify(productRepository).findById(product.getId());
        verify(productRepository).delete(any(Product.class));
    }

    @Test
    public void findProductByNameTest() {
        // given
        String productName = "productName1";
        Product product = products.get(0);

        // Mock the repository behavior for findByName
        when(productRepository.findByName(productName)).thenReturn(Optional.of(product));

        // when
        Optional<Product> foundProduct = productService.findByName(productName);

        // then
        verify(productRepository).findByName(productName);
        assertTrue(foundProduct.isPresent());
        assertEquals(productName, foundProduct.get().getName());
    }
}