package com.sparta.shopapi.domain.product.dto;

import com.sparta.shopapi.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ProductListResponseDto {


    private List<ProductListDto> productList;
    private int totalPage; // 전체 페이지
    private int currentPage; // 현재 페이지


    public ProductListResponseDto(Page<Product> products) {
        // List<product>
        totalPage = products.getTotalPages();
        currentPage = products.getNumber() +1;
        productList = products.getContent().stream()
                .map(ProductListDto::new).toList();
    }

    @Getter
    private class ProductListDto {
        private Long id;
        private String productName;
        private Integer price;
        private String category;


        public ProductListDto(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.price = product.getPrice();
            this.category = product.getCategory();
        }
    }


}
