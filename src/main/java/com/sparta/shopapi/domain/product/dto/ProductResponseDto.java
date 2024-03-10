package com.sparta.shopapi.domain.product.dto;

import com.sparta.shopapi.domain.product.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private Long id;

    private String productName;

    private Integer price;

    private Integer quantity;

    private String description;

    private String category;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.category = product.getCategory();
    }
}
