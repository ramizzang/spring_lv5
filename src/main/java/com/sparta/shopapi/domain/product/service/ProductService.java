package com.sparta.shopapi.domain.product.service;

import com.sparta.shopapi.domain.product.dto.ProductListResponseDto;
import com.sparta.shopapi.domain.product.dto.RegisterProductRequestDto;
import com.sparta.shopapi.domain.product.dto.ProductResponseDto;
import com.sparta.shopapi.domain.product.entity.Product;
import com.sparta.shopapi.domain.product.repository.ProductRepository;
import com.sparta.shopapi.global.handler.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta.shopapi.global.handler.exception.ErrorCode.NOT_FOUND_PRODUCT_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDto registerProduct(RegisterProductRequestDto requestDto) {
        Product product = productRepository.save(requestDto.toEntity());
        return new ProductResponseDto(product);
    }

    public ProductResponseDto readProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new CustomApiException(NOT_FOUND_PRODUCT_ID.getMessage(), HttpStatus.NOT_FOUND)
        );
        return new ProductResponseDto(product);
    }

    @Transactional(readOnly = true)
    public ProductListResponseDto readProductList(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productRepository.findAll(pageable);


        return new ProductListResponseDto(products);
    }
}
