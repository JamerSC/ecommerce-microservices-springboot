package com.ecommerce.product.service;

import com.ecommerce.product.dto.CreateProductDto;
import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.dto.UpdateProductDto;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.repository.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    public static final Long DEFAULT_USER_ID = 1L; // Replace with actual user ID retrieval logic
    public static final OffsetDateTime DEFAULT_TIMESTAMP = OffsetDateTime.now(); // Replace with actual timestamp retrieval logic

    @Override
    public Page<ProductDto> getAllProducts(
            String search,
            LocalDate dateFrom,
            LocalDate dateTo,
            Pageable pageable
    ) {
        log.debug("Fetching products with search: {}, dateFrom: {}, dateTo: {}, pageable: {}", search, dateFrom, dateTo, pageable);

        Specification<Product> spec = Specification.allOf(
                ProductSpecification.search(search),
                ProductSpecification.dateBetween(dateFrom, dateTo)
        );

        return productRepository.findAll(spec, pageable)
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getCreatedBy(),
                        product.getCreatedAt(),
                        product.getUpdatedBy(),
                        product.getUpdatedAt()
                ));
    }

    @Override
    public List<ProductDto> getAllMockProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stockQuantity(product.getStockQuantity())
                        .createBy(product.getCreatedBy())
                        .createdAt(product.getCreatedAt())
                        .updateBy(product.getUpdatedBy())
                        .updatedAt(product.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCreatedBy(),
                product.getCreatedAt(),
                product.getUpdatedBy(),
                product.getUpdatedAt()
        );
    }

    @Override
    public ProductDto createProduct(CreateProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setCreatedBy(DEFAULT_USER_ID);
        product.setCreatedAt(DEFAULT_TIMESTAMP);
        product.setUpdatedBy(DEFAULT_USER_ID);
        product.setUpdatedAt(DEFAULT_TIMESTAMP);

        Product savedProduct = productRepository.save(product);

        return new ProductDto(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getStockQuantity(),
                savedProduct.getCreatedBy(),
                savedProduct.getCreatedAt(),
                savedProduct.getUpdatedBy(),
                savedProduct.getUpdatedAt()
        );
    }

    @Override
    public ProductDto updateProduct(Long id, UpdateProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setCreatedBy(DEFAULT_USER_ID);
        product.setCreatedAt(DEFAULT_TIMESTAMP);
        product.setUpdatedBy(DEFAULT_USER_ID);
        product.setUpdatedAt(DEFAULT_TIMESTAMP);

        Product updatedProduct = productRepository.save(product);

        return new ProductDto(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getStockQuantity(),
                updatedProduct.getCreatedBy(),
                updatedProduct.getCreatedAt(),
                updatedProduct.getUpdatedBy(),
                updatedProduct.getUpdatedAt()
        );
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
