package com.sparta.shopapi.domain.cart.controller;

import com.sparta.shopapi.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
}
