package com.sparta.shopapi.domain.cart.service;

import com.sparta.shopapi.domain.cart.dto.CartRequestDto;
import com.sparta.shopapi.domain.cart.dto.CartResponseDto;
import com.sparta.shopapi.domain.cart.dto.CartListResponseDto;
import com.sparta.shopapi.domain.cart.dto.CartListResponseDto.CartListDto;
import com.sparta.shopapi.domain.cart.entity.Cart;
import com.sparta.shopapi.domain.cart.repository.CartRepository;
import com.sparta.shopapi.domain.member.entity.Member;
import com.sparta.shopapi.domain.product.entity.Product;
import com.sparta.shopapi.domain.product.repository.ProductRepository;
import com.sparta.shopapi.global.handler.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.shopapi.global.handler.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartResponseDto addCart(CartRequestDto requestDto, Member member) {

        // 물건 정보 가져오기
        Long productId = requestDto.getProductId();
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new CustomApiException(NOT_FOUND_PRODUCT_ID.getMessage(), HttpStatus.NOT_FOUND)
        );

        // 재고확인
        checkStock(product.getQuantity(), requestDto.getQuantity());

        // 둘다 통과 주문
        // 1. 상품 재고 줄이기
        product.decressStock(requestDto.getQuantity());

        // 2. 카트에 유저, 상품, 수량 저장
        Cart cart = cartRepository.findByMemberAndProduct(member, product)
                .orElseGet(() -> cartRepository.save(requestDto.toEntity(member, product)));

        // 기존과 동일한 상품이 있다.
        // 기존 장바구니 수량에 수량 추가
        cart.addQuantity(requestDto.getQuantity());

        return new CartResponseDto(cart);
    }

    @Transactional
    public CartListResponseDto getCartList(Member member) {
        // 장바구니 조회
        List<Cart> carts = cartRepository.findAllByMember(member);

        Integer totalPrice = 0;

        List<CartListDto> cartList = new ArrayList<>();
        for (Cart cart : carts) {
            //장바구니 상품 하나의 total 가격
            Integer quantity = cart.getQuantity(); // 장바구니에 담긴 수량
            Integer price = cart.getProduct().getPrice(); // 상품 가격
            Integer productTotalPrice = quantity * price;

            CartListDto cartListdto = CartListDto.builder()
                    .cartId(cart.getId())
                    .productName(cart.getProduct().getProductName())
                    .quantity(quantity)
                    .price(price)
                    .productTotalPrice(productTotalPrice)
                    .build();

            cartList.add(cartListdto); // List에 추가

            totalPrice += productTotalPrice; // 전체 상품 가격
        }

        return new CartListResponseDto(member.getId(), member.getEmail(), cartList, totalPrice);
    }


    // 장바구니 상품 수량 수정
    @Transactional
    public CartResponseDto updateCart(Long cartId, CartRequestDto requestDto, Member member) {
        // 상품 있는지 확인
        Cart cart = checkCart(cartId);
        // 본인 확인
        checkOwnership(cart.getMember(), member);

        Integer productQuantity = cart.getProduct().getQuantity(); // 상품 재고 수량
        Integer cartQuantity = cart.getQuantity(); // 장바구니 상품 수량
        Integer requestQuantity = requestDto.getQuantity(); // 수정 요청 수량

        // 재고확인
        checkStock(productQuantity, requestQuantity);

        // 수량 수정
        cart.update(requestQuantity);

        // 재고 수정
        // 기존 수량 < 변경 수량
        if (cartQuantity < requestQuantity) {
            // 재고감소
            cart.getProduct().decressStock(requestQuantity - cartQuantity);
        }
        // 변경 수량 < 기존수량
        if (requestQuantity < cartQuantity) {
            // 재고 증가
            cart.getProduct().increaseStock(cartQuantity - requestQuantity);
        }

        return new CartResponseDto(cart);
    }


    // 장바구니 상품 삭제
    @Transactional
    public void deleteCart(Long cartId, Member member) {
        // 장바구니 상품 있는지 확인
        Cart cart = checkCart(cartId);
        // 본인 확인
        checkOwnership(cart.getMember(), member);
        // 재고 수량 늘리기
        cart.getProduct().increaseStock(cart.getQuantity());
        // 장바구니 삭제
        cartRepository.delete(cart);

    }


    // 재고확인
    private boolean checkStock(Integer productQuantity, Integer requestQuantity) {
        if (productQuantity < requestQuantity) { //상품재고 < 추가수량
            throw new CustomApiException(INSUFFICIENT_STOCK.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return true;
    }


    // 존재하는 장바구니 확인
    public Cart checkCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new CustomApiException(NOT_FOUND_CART_ID.getMessage(), HttpStatus.NOT_FOUND)
        );
        return cart;
    }


    // 본인 확인
    private boolean checkOwnership(Member cartMember, Member member) {
        if (cartMember.getEmail().equals(member.getEmail())) {
            return true;
        }
        throw new CustomApiException(UNAUTHORIZED_ACCESS_TO_CART.getMessage(), HttpStatus.FORBIDDEN);
    }

}
