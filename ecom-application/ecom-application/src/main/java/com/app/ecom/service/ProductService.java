package com.app.ecom.service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
import com.app.ecom.repository.ProductRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRespository productRespository;
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductfromRequest(product,productRequest);
        Product savedProducts =  productRespository.save(product);
       return mapToResponseResponse(savedProducts);
    }

    // Helper methods
    private void updateProductfromRequest(Product product, ProductRequest productRequest) {
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setCategory(productRequest.getCategory());
            product.setDescription(productRequest.getDescription());
            product.setStockQuantity(productRequest.getStockQuantity());
            product.setImageUrl(productRequest.getImageUrl());
    }

    private ProductResponse mapToResponseResponse(Product savedProducts) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProducts.getId());
        productResponse.setName(savedProducts.getName());
        productResponse.setActive(savedProducts.getActive());
        productResponse.setDescription(savedProducts.getDescription());
        productResponse.setPrice(savedProducts.getPrice());
        productResponse.setCategory(savedProducts.getCategory());
        productResponse.setStockQuantity(savedProducts.getStockQuantity());
        productResponse.setImageUrl(savedProducts.getImageUrl());
        return productResponse;
    }


    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productResquest) {
        return productRespository.findById(id)
                .map(existingUser -> {
                    updateProductfromRequest(existingUser,productResquest);
                    Product savedProduct = productRespository.save(existingUser);
                    return mapToResponseResponse(savedProduct);
                });
    }


    public List<ProductResponse> getAllProducts() {
        return productRespository.findByActiveTrue()
                .stream()
                .map(this::mapToResponseResponse)
                .collect(Collectors.toList());

    }

    public boolean deleteProduct(Long id) {
        return productRespository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRespository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRespository.searchProducts(keyword)
                .stream()
                .map(this::mapToResponseResponse)
                .collect(Collectors.toList());
    }
}
