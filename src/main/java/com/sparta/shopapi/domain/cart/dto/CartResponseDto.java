package com.sparta.shopapi.domain.cart.dto;

import com.sparta.shopapi.domain.cart.entity.Cart;
import lombok.Getter;

@Getter
public class CartResponseDto {
    private Long id;
    private String email;
    private String productName;
    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.email = cart.getMember().getEmail();
        this.productName = cart.getProduct().getProductName();
    }
}
