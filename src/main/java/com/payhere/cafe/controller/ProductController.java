package com.payhere.cafe.controller;


import com.payhere.cafe.dto.request.OnlyKeywordDTO;
import com.payhere.cafe.dto.request.OnlyPageDTO;
import com.payhere.cafe.dto.request.OnlyProductIdDTO;
import com.payhere.cafe.dto.request.ProductDTO;
import com.payhere.cafe.dto.response.Response;
import com.payhere.cafe.entity.Product;
import com.payhere.cafe.service.ProductServiceImpl;
import com.payhere.cafe.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;
    private UserServiceImpl userService;
    private final Response response;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO product) {
        try {
            return productService.insertProduct(product);
        } catch(Exception e) {
            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product) {
        try {
            return productService.updateProduct(product);
        } catch(Exception e) {
            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestBody OnlyProductIdDTO productId) {
        try {
            return productService.deleteProduct(productId.getProductid());
        } catch(Exception e) {
            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getProductDetail(@RequestBody OnlyProductIdDTO productId) {
        try {
            return ResponseEntity.ok().body(productService.getProductDetail(productId.getProductid()));
        } catch(Exception e) {
            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProductList(@RequestBody OnlyPageDTO page) {
        try {
            return ResponseEntity.ok().body(productService.getProductList(page.getPage()));
        } catch(Exception e) {
            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProductList(@RequestBody OnlyKeywordDTO keyword) {
        try {
            return ResponseEntity.ok().body(productService.searchProductList(keyword.getKeyword()));
        } catch(Exception e) {
            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
