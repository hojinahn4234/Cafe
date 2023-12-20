package com.payhere.cafe.entity;

import com.payhere.cafe.dto.request.ProductDTO;
import com.payhere.cafe.util.NameConverter;
import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private Long productid;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Long price;

    @Column(name = "cost")
    private Long cost;

    @Column(name = "name")
    private String name;

    @Column(name = "charname")
    private String charname;

    @Column(name = "description")
    private String description;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "expirationdate")
    private String expirationdate;

    @Column(name = "size")
    private String size;

    @Builder
    public Product(String category, Long price, Long cost, String name, String charname, String description, String barcode, String expirationdate, String size) {
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.charname = NameConverter.toConsonant(name);
        this.description = description;
        this.barcode = barcode;
        this.expirationdate = expirationdate;
        this.size = size;
    }

    public void update(ProductDTO productRequestDTO) {
        this.category = productRequestDTO.getCategory();
        this.price = productRequestDTO.getPrice();
        this.cost = productRequestDTO.getCost();
        this.name = productRequestDTO.getName();
        this.charname = NameConverter.toConsonant(productRequestDTO.getName());
        this.description = productRequestDTO.getDescription();
        this.barcode = productRequestDTO.getBarcode();
        this.expirationdate = productRequestDTO.getExpirationdate();
        this.size = productRequestDTO.getSize();
    }
}