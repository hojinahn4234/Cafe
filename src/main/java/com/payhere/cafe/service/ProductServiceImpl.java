package com.payhere.cafe.service;

import com.payhere.cafe.domain.dto.response.ProdDetailResponseDTO;
import com.payhere.cafe.entity.Product;
import com.payhere.cafe.exception.ResourceNotFoundException;
import com.payhere.cafe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository repository;

    @Override
    public int insertProduct(Product product) {
        if(repository.save(product) != null) {
            return 200;
        } else {
            return 1;
        }
    }

    @Override
    public int updateProduct(Product product) {
        if(repository.save(product) != null) {
            return 200;
        } else {
            return 1;
        }
    }

    @Override
    public int deleteProduct(Long productId) {
        if(repository.existsById(productId)) {
            repository.deleteById(productId);
        } else {
            return 1;
        }
        return 200;
    }

    @Override
    public List<ProdDetailResponseDTO> getProductList(int page) {
        Page<Product> productsPage = repository
                .findAll(PageRequest.of(page - 1, 10));
        return productsPage.getContent().stream()
                .map(this::mapToProdDetailResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductDetail(Long productId) {
        return repository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "product", "productId", String.valueOf(productId)
                ));
    }

    @Override
    public List<ProdDetailResponseDTO> searchProductList(String keyword) {
        return repository
                .findAllNameContaining(keyword)
                .stream()
                .map(this::mapToProdDetailResponseDTO)
                .collect(Collectors.toList());
    }


    public ProdDetailResponseDTO mapToProdDetailResponseDTO(Product product) {
        ProdDetailResponseDTO dto = new ProdDetailResponseDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());

        return dto;
    }
}
