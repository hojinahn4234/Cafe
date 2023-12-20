package com.payhere.cafe.dto.request;

import com.payhere.cafe.entity.Product;
import com.payhere.cafe.util.NameConverter;
import lombok.Data;

@Data
public class ProductDTO {
    private Long productid;

    private String category;

    private Long price;

    private Long cost;

    private String name;

    private String description;

    private String barcode;

    private String expirationdate;

    private String size;

    public Product toEntity() {
        return Product.builder()
                .category(getCategory())
                .price(getPrice())
                .cost(getCost())
                .name(getName())
                .charname(NameConverter.toConsonant(getName()))
                .description(getDescription())
                .barcode(getBarcode())
                .expirationdate(getExpirationdate())
                .size(getSize())
                .build();
    }

    public static ProductDTO fromEntity(Product product) {
        ProductDTO prod = new ProductDTO();
        prod.setProductid(product.getProductid());
        prod.setCategory(product.getCategory());
        prod.setPrice(product.getPrice());
        prod.setCost(product.getCost());
        prod.setName(product.getName());
        prod.setDescription(product.getDescription());
        prod.setBarcode(product.getBarcode());
        prod.setExpirationdate(product.getExpirationdate());
        prod.setSize(product.getSize());
        return prod;
    }

}
