package com.sparta.shopapi.domain.product.controller;

import com.sparta.shopapi.domain.member.service.MemberService;
import com.sparta.shopapi.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
}
