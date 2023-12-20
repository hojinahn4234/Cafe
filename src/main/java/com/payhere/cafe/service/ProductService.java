package com.payhere.cafe.service;

import com.payhere.cafe.dto.request.ProductDTO;
import com.payhere.cafe.dto.response.ProdDetailResponseDTO;
import com.payhere.cafe.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> insertProduct(ProductDTO product);

    ResponseEntity<?> updateProduct(ProductDTO product);

    ResponseEntity<?> deleteProduct(Long productId);

    List<ProductDTO> getProductList(int page);

    ProductDTO getProductDetail(Long productId);

    List<ProductDTO> searchProductList(String keyword);
}
