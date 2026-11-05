package com.ecommerce.product.service;

import com.ecommerce.product.dto.CreateProductDto;
import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.dto.UpdateProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    Page<ProductDto> getAllProducts(
            String search,
            LocalDate dateFrom,
            LocalDate dateTo,
            Pageable pageable
    );

    List<ProductDto> getAllMockProducts();

    ProductDto getProductById(Long id);

    ProductDto createProduct(CreateProductDto productDto);

    ProductDto updateProduct(Long id, UpdateProductDto productDto);

    void deleteProduct(Long id);
}
