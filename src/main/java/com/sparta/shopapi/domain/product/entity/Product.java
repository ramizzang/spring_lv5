package com.sparta.shopapi.domain.product.entity;

import com.sparta.shopapi.domain.cart.entity.Cart;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="product_name", nullable = false)
    private String productName;

    @Column(name="price", nullable = false)
    private Integer price;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name ="description")
    private String description;

    @Column(name="category", nullable = false)
    private String category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();

    @Builder
    public Product(String productName, Integer price, Integer quantity, String description, String category) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
    }
}
