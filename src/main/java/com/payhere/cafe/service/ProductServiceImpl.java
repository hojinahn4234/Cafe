package com.payhere.cafe.service;

import com.payhere.cafe.dto.response.ProdDetailResponseDTO;
import com.payhere.cafe.entity.Product;
import com.payhere.cafe.exception.ResourceNotFoundException;
import com.payhere.cafe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository repository;

    @Override
    public int insertProduct(Product product) {
        repository.save(product);
        return 200;
    }

    @Override
    public int updateProduct(Product product) {
        repository.save(product);
        return 200;
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
                .collect(Collectors.toList())
                .stream().sorted(Comparator.comparing(ProdDetailResponseDTO::getProductId).reversed())
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
                .findAllByNameContaining(keyword)
                .stream()
                .map(this::mapToProdDetailResponseDTO)
                .collect(Collectors.toList());
    }


    public ProdDetailResponseDTO mapToProdDetailResponseDTO(Product product) {
        ProdDetailResponseDTO prodDetailResponseDTO = new ProdDetailResponseDTO();
        prodDetailResponseDTO.setProductId(product.getProductid());
        prodDetailResponseDTO.setName(product.getName());

        return prodDetailResponseDTO;
    }
}
