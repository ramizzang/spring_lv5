package com.sparta.shopapi.domain.product.repository;

import com.sparta.shopapi.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
