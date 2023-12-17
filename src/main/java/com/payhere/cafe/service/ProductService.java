package com.payhere.cafe.service;

import com.payhere.cafe.dto.response.ProdDetailResponseDTO;
import com.payhere.cafe.entity.Product;

import java.util.List;

public interface ProductService {
    int insertProduct(Product product);

    int updateProduct(Product product);

    int deleteProduct(Long productId);

    List<ProdDetailResponseDTO> getProductList(int page);

    Product getProductDetail(Long productId);

    List<ProdDetailResponseDTO> searchProductList(String keyword);
}
