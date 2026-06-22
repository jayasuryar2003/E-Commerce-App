package com.app.ecom.controller;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    @PostMapping
    public ResponseEntity<ProductResponse>  createProduct(@RequestBody ProductRequest productResquest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productResquest));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
     return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse>  updateProduct(@PathVariable Long id,@RequestBody ProductRequest productResquest) {
        return productService.updateProduct(id,productResquest)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteProduct(@PathVariable Long id) {
         productService.deleteProduct(id);
        return productService.deleteProduct(id)?ResponseEntity.noContent().build():ResponseEntity.notFound().build();

    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>>  searchProduct(@RequestParam String keyword) {
      return ResponseEntity.ok(productService.searchProducts(keyword));

    }

}
