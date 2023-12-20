package com.payhere.cafe.repository;

import com.payhere.cafe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query(value = "update Product p set p.category = category" +
            ", p.price = price " +
            ", p.cost = cost" +
            ", p.name = name" +
            ", p.charname = charname" +
            ", p.description = description" +
            ", p.barcode = barcode" +
            ", p.expirationdate = expirationdate" +
            ", p.size = size" +
            " where p.productid = productid"
    )
    void setProductByProductid(
            Long productid,
            String category,
            Long price,
            Long cost,
            String name,
            String charname,
            String description,
            String barcode,
            String expirationdate,
            String size
    );

    Product findByProductid(Long productid);

    @Query(value = "select * from product p where p.name like %:keyword%", nativeQuery = true)
    List<Product> findAllByNameContaining(@Param("keyword") String keyword);

    @Query (value = "select * from product p where p.charname like %:keyword%", nativeQuery = true)
    List<Product> findByCharnameContaining(@Param("keyword") String keyword);
}
