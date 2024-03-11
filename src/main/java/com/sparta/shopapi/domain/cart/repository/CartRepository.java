package com.sparta.shopapi.domain.cart.repository;

import com.sparta.shopapi.domain.cart.entity.Cart;
import com.sparta.shopapi.domain.member.entity.Member;
import com.sparta.shopapi.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByMemberAndProduct(Member member, Product product);

    List<Cart> findAllByMember(Member member);


}
