package com.ecommerce.product.service;

import com.ecommerce.product.dto.CreateProductDto;
import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.dto.UpdateProductDto;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    // other mocks

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        Product currentProduct = new Product();
        currentProduct.setId(1L);
        currentProduct.setName("Product 1");
        currentProduct.setDescription("Product 1 lorem ipsum ...");
        currentProduct.setPrice(BigDecimal.valueOf(50));
        currentProduct.setStockQuantity(50);
        currentProduct.setCreatedBy(1L);
        currentProduct.setCreatedAt(OffsetDateTime.now());
        currentProduct.setUpdatedBy(1L);
        currentProduct.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExist() {

        // Arrange (Object)
        Product product = new Product();
        product.setId(2L);
        product.setName("Product 2");
        product.setDescription("Product 2 lorem ipsum ...");
        product.setPrice(BigDecimal.valueOf(50));
        product.setStockQuantity(50);
        product.setCreatedBy(1L);
        product.setCreatedAt(OffsetDateTime.now());
        product.setUpdatedBy(1L);
        product.setUpdatedAt(OffsetDateTime.now());

        when(productRepository.findById(2L))
                .thenReturn(Optional.of(product)); // if product not exist fall to mock product

        // Act
        ProductDto result = productService.getProductById(2L);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Product 2", result.getName());
        assertEquals(50, result.getStockQuantity());

        verify(productRepository).findById(2L);
    }

    // ==========================================
    // CREATE PRODUCT
    // ==========================================

    @Test
    void createProduct_ShouldCreateProduct_WhenDataIsValid() {
        // arrange
        CreateProductDto dto = new CreateProductDto();

        dto.setName("Product 3");
        dto.setDescription("Product 3 lorem ipsum ...");
        dto.setPrice(BigDecimal.valueOf(100));
        dto.setStockQuantity(10);

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        ProductDto result = productService.createProduct(dto);

        // assert
        assertNotNull(result);
        assertEquals("Product 3", result.getName());
        assertEquals(BigDecimal.valueOf(100), dto.getPrice());
        assertEquals(10,dto.getStockQuantity());

        verify(productRepository).save(any(Product.class));
    }

    // ==========================================
    // UPDATE PRODUCT
    // ==========================================

    @Test
    void updateProduct_ShouldUpdateProduct_WhenProductExists() {
        // arrange
        Product existingProduct = new Product();
        existingProduct.setId(4L);
        existingProduct.setName("Product 40");
        existingProduct.setDescription("Product 40 lorem ipsum ...");
        existingProduct.setStockQuantity(40);
        existingProduct.setCreatedBy(1L);
        existingProduct.setCreatedAt(OffsetDateTime.now());
        existingProduct.setUpdatedBy(1L);
        existingProduct.setUpdatedAt(OffsetDateTime.now());

        // update product
        UpdateProductDto dto = new UpdateProductDto();
        dto.setName("Product 4");
        dto.setDescription("Product 4 lorem ipsum ...");
        dto.setStockQuantity(10);

        when(productRepository.findById(4L))
                .thenReturn(Optional.of(existingProduct));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        ProductDto result = productService.updateProduct(4L, dto);

        // assert
        assertNotNull(result);

        assertEquals("Product 4", result.getName());
        assertEquals("Product 4 lorem ipsum ...", result.getDescription());
        assertEquals(10, result.getStockQuantity());

        verify(productRepository).findById(4L);
        verify(productRepository).save(existingProduct);
    }

    // ==========================================
    // DELETE PRODUCT
    // ==========================================

    @Test
    void deleteProduct_ShouldDeleteProduct_WhenCalled() {
        // arrange
        Long orderId = 1L;

        // act
        productService.deleteProduct(orderId);

        // assert
        verify(productRepository).deleteById(orderId);
    }
}
