package com.sparta.shopapi.domain.product.dto;

import com.sparta.shopapi.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Schema(description = "Porduct Register request")
public class RegisterProductRequestDto {

    @NotBlank(message = "상품명을 입력해 주세요")
    @Schema(description = "상품명", example = "동서식품 포스트 오곡 코코볼 570g")
    private String productName;

    @NotNull(message = "상품 가격을 입력해 주세요")
    @Positive(message = "유효한 값을 입력하세요")
    @Schema(description = "상품 가격", example = "5300")
    private Integer price;

    @NotNull(message = "상품 수량을 입력해 주세요")
    @PositiveOrZero // 0 포함
    @Schema(description = "상품 수량", example = "100")
    private Integer quantity;


    @Schema(description = "상품 설명", example = "아이들을 위한 영양만점 시리얼 오곡 코코볼")
    private String description;

    @NotBlank(message = "상품 카테고리를 입력해주세요.")
    @Schema(description = "상품 카테고리", example = "식품")
    private String category;

    @Builder
    public RegisterProductRequestDto(String productName, Integer price, Integer quantity, String description, String category) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
    }

    public Product toEntity(){
        return Product.builder()
                .productName(productName)
                .price(price)
                .quantity(quantity)
                .description(description)
                .category(category)
                .build();
    }
}
