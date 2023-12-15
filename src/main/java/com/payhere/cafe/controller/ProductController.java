package com.payhere.cafe.controller;


import com.payhere.cafe.entity.Product;
import com.payhere.cafe.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok().body(service.insertProduct(product));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok().body(service.updateProduct(product));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestBody Long productId) {
        return ResponseEntity.ok().body(service.deleteProduct(productId));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getProductDetail(@RequestBody Long productId) {
        return ResponseEntity.ok().body(service.getProductDetail(productId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProductList(@RequestBody int page) {
        return ResponseEntity.ok().body(service.getProductList(page));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductList(@RequestBody String keyword) {
        return ResponseEntity.ok().body(service.searchProductList(keyword));
    }
}
