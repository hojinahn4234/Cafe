package com.payhere.cafe.domain.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String category;

    private Long price;

    private Long cost;

    private String name;

    private String description;

    private String barcode;

    private Date expirationDate;

    private String size;

    public static class ProductController {
    }
}