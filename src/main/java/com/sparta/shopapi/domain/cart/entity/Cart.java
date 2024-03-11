package com.sparta.shopapi.domain.cart.entity;

import com.sparta.shopapi.domain.member.entity.Member;
import com.sparta.shopapi.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Cart(Integer quantity, Member member, Product product) {
        this.quantity = quantity;
        this.member = member;
        this.product = product;
    }

    public void addQuantity(Integer quantity) { // 카트에 이미 추가 됐을때 수량 추가
        this.quantity += quantity;
    }

    public void update(Integer quantity) {
        if(quantity>0){
        this.quantity = quantity;
        }
    }
}
