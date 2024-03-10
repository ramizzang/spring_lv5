package com.sparta.shopapi.domain.product.service;

import com.sparta.shopapi.domain.product.dto.RegisterProductRequestDto;
import com.sparta.shopapi.domain.product.dto.RegisterProductResponseDto;
import com.sparta.shopapi.domain.product.entity.Product;
import com.sparta.shopapi.domain.product.repository.ProductRepository;
import com.sparta.shopapi.global.handler.exception.CustomApiException;
import com.sparta.shopapi.global.handler.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sparta.shopapi.global.handler.exception.ErrorCode.NOT_FOUND_PRODUCT_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public RegisterProductResponseDto registerProduct(RegisterProductRequestDto requestDto) {
        Product product = productRepository.save(requestDto.toEntity());
        return new RegisterProductResponseDto(product);
    }

    public RegisterProductResponseDto readProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new CustomApiException(NOT_FOUND_PRODUCT_ID.getMessage())
        );
        return new RegisterProductResponseDto(product);
    }
}
