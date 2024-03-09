package com.sparta.shopapi.domain.cart.controller;

import com.sparta.shopapi.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
}
