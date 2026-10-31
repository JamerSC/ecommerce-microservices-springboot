package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Override
    public List<ProductDto> getAllProducts() {
        return List.of();
    }

    @Override
    public ProductDto getProductById(Long id) {
        return null;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
