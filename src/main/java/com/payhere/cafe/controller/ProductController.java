package com.payhere.cafe.controller;


import com.payhere.cafe.domain.dto.request.ProductListDTO;
import com.payhere.cafe.domain.dto.request.ProductSearchDTO;
import com.payhere.cafe.domain.dto.response.ProdDetailResponseDTO;
import com.payhere.cafe.entity.Product;
import com.payhere.cafe.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

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
        return ResponseEntity.ok().body(service.getProductDetail(productId))
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProductList() {
        return ResponseEntity.ok().body(service.getProductList());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductList(@RequestBody ProductSearchDTO productSearch) {
        return ResponseEntity.ok().body(service.searchProductList(productSearch));
    }
}
