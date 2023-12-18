package com.payhere.cafe.entity;



import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Data
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

    @Column(name = "description")
    private String description;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "expirationdate")
    private Date expirationdate;

    @Column(name = "size")
    private String size;
}