package com.payhere.cafe.repository;

import com.payhere.cafe.domain.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDTO, Long> {

}
