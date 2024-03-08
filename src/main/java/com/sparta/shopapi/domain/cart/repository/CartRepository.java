package com.sparta.shopapi.domain.cart.repository;

import com.sparta.shopapi.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
