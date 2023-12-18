package com.payhere.cafe.controller;


import com.payhere.cafe.dto.response.Response;
import com.payhere.cafe.entity.Product;
import com.payhere.cafe.service.ProductServiceImpl;
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
    private ProductServiceImpl service;
    private final Response response;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        int code = service.insertProduct(product);
        if(code == 200) {
            return response.success("", "ok", HttpStatus.OK);
        }
        else if(code == 2) {
            return response.fail("", HttpStatus.BAD_REQUEST);
        }
        else {
            return response.fail( "", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        int code = service.updateProduct(product);
        if(code == 200) {
            return response.success("", "ok", HttpStatus.OK);
        }
        else if(code == 2) {
            return response.fail("", HttpStatus.BAD_REQUEST);
        }
        else {
            return response.fail( "", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestBody Long productId) {
        int code = service.deleteProduct(productId);
        if(code == 200) {
            return response.success("", "ok", HttpStatus.OK);
        }
        else if(code == 2) {
            return response.fail("", HttpStatus.BAD_REQUEST);
        }
        else {
            return response.fail( "", HttpStatus.BAD_REQUEST);
        }
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
