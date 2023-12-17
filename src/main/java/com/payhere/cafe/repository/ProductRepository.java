package com.payhere.cafe.repository;

import com.payhere.cafe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllNameContaining(String keyword);

    Optional<Product> findById(Long productId);
}
