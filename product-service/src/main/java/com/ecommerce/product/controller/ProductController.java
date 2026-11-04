package com.ecommerce.product.controller;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ecommerce/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<ProductDto> orders = productService.getAllProducts(
                search,
                dateFrom,
                dateTo,
                pageable
        );
        return ResponseEntity.ok(ApiResponse.<Page<ProductDto>>builder()
                .success(true)
                .message("Products retrieved successfully")
                .data(orders)
                .status(200)
                .timestamp(java.time.LocalDateTime.now())
                .build()
        );
    }

    @GetMapping("/mock")
    public List<ProductDto> getMockProducts() {
        // inline mock data for testing purposes
        return List.of(
                new ProductDto(1L, "Product 1", "Description 1", BigDecimal.valueOf(10.0), 100, null, null, null, null),
                new ProductDto(2L, "Product 2", "Description 2", BigDecimal.valueOf(20.0), 200, null, null, null, null),
                new ProductDto(3L, "Product 3", "Description 3", BigDecimal.valueOf(30.0), 300, null, null, null, null)
        );

//        List<ProductDto> mockProducts = List.of(
//                new ProductDto(1L, "Product 1", "Description 1", BigDecimal.valueOf(10.0), 100, null, null, null, null),
//                new ProductDto(2L, "Product 2", "Description 2", BigDecimal.valueOf(20.0), 200, null, null, null, null),
//                new ProductDto(3L, "Product 3", "Description 3", BigDecimal.valueOf(30.0), 300, null, null, null, null)
//        );
//        return mockProducts;
    }
}
