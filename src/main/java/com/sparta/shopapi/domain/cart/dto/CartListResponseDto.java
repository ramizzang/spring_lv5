package com.sparta.shopapi.domain.cart.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartListResponseDto {
    private Long memberId;
    private String email;
    private List<CartListDto> cartList;
    private Integer totalPrice;

    public CartListResponseDto(Long memberId, String email, List<CartListDto> cartList, Integer totalPrice) {
        this.memberId = memberId;
        this.email = email;
        this.cartList = cartList;
        this.totalPrice = totalPrice;
    }

    @Getter
    public static class CartListDto{
        private Long cartId;
        private String productName;
        private Integer quantity;
        private Integer price;
        private Integer productTotalPrice; // 해당 상품 총 가격

        @Builder
        public CartListDto(Long cartId, String productName, Integer quantity, Integer price, Integer productTotalPrice) {
            this.cartId = cartId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.productTotalPrice = productTotalPrice;
        }
    }
}

