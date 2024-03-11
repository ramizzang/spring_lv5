package com.sparta.shopapi.domain.cart.dto;


import com.sparta.shopapi.domain.cart.entity.Cart;
import com.sparta.shopapi.domain.member.entity.Member;
import com.sparta.shopapi.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Schema(description = "Cart request")
public class CartRequestDto {
    @NotNull(message = "상품아이디를 입력하세요")
    private Long productId;

    @Min(value = 1,message = "주문 가능한 최소 수랑은 1개 입니다.")
    private Integer quantity = 1;

    @Builder
    public CartRequestDto(Integer quantity) {
        this.quantity = quantity;
    }

    public Cart toEntity(Member member, Product product){
        return Cart.builder()
                .member(member)
                .product(product)
                .quantity(quantity)
                .build();
    }

}
