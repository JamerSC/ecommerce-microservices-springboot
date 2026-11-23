package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductDto;
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
}
