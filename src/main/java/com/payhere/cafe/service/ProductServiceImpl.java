package com.payhere.cafe.service;

import com.payhere.cafe.dto.request.ProductDTO;
import com.payhere.cafe.dto.response.Response;
import com.payhere.cafe.entity.Product;
import com.payhere.cafe.exception.ResourceNotFoundException;
import com.payhere.cafe.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    @Autowired
    private final ProductRepository repository;
    private final Response response;

    @Override
    public ResponseEntity<?> insertProduct(ProductDTO product) {
        if(product.getProductid() == null) {
            repository.save(product.toEntity());
            return response.success(null, "ok", HttpStatus.OK);
        }
        else {
            return response.fail("상품 ID를 임의로 입력하실 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateProduct(ProductDTO product) {
        if(repository.existsById(product.getProductid())) {
            Product prod = repository.findByProductid(product.getProductid());
            prod.update(product);
            repository.setProductByProductid(
                    prod.getProductid(),
                    prod.getCategory(),
                    prod.getPrice(),
                    prod.getCost(),
                    prod.getName(),
                    prod.getCharname(),
                    prod.getDescription(),
                    prod.getBarcode(),
                    prod.getExpirationdate(),
                    prod.getSize()
            );
            return response.success(null, "ok", HttpStatus.OK);
        }
        else {
            return response.fail("존재하는 상품의 ID를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long productId) {
        if(repository.existsById(productId)) {
            repository.deleteById(productId);
            return response.success(null, "ok", HttpStatus.OK);
        } else {
            return response.fail("존재하는 상품의 ID를 입력해주세요", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<ProductDTO> getProductList(int page) {
        Page<Product> productsPage = repository
                .findAll(PageRequest.of(page - 1, 10));
        return productsPage.getContent().stream()
                .map(ProductDTO::fromEntity)
                .toList()
                .stream()
                .sorted(Comparator.comparing(ProductDTO::getProductid))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductDetail(Long productId) {
        return ProductDTO.fromEntity(repository.findByProductid(productId));
    }

    @Override
    public List<ProductDTO> searchProductList(String keyword) {
        if(Pattern.matches("^[ㄱ-ㅎ]+$", keyword)) {
            return repository
                    .findByCharnameContaining(keyword)
                    .stream()
                    .map(product -> ProductDTO.fromEntity(product))
                    .collect(Collectors.toList());
        }
        else {
            return repository
                    .findAllByNameContaining(keyword)
                    .stream()
                    .map(product -> ProductDTO.fromEntity(product))
                    .collect(Collectors.toList());
        }
    }
}
