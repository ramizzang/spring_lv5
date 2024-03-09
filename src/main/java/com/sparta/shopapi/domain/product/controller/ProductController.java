package com.sparta.shopapi.domain.product.controller;

import com.sparta.shopapi.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
}
