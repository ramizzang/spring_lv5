package com.sparta.shopapi.domain.product.service;

import com.sparta.shopapi.domain.product.dto.RegisterProductRequestDto;
import com.sparta.shopapi.domain.product.dto.RegisterProductResponseDto;
import com.sparta.shopapi.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public RegisterProductResponseDto registerProduct(RegisterProductRequestDto requestDto) {

        return new RegisterProductResponseDto(productRepository.save(requestDto.toEntity()));
    }
}
