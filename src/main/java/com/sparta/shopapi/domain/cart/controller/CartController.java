package com.sparta.shopapi.domain.cart.controller;

import com.sparta.shopapi.domain.cart.dto.CartRequestDto;
import com.sparta.shopapi.domain.cart.dto.CartResponseDto;
import com.sparta.shopapi.domain.cart.dto.CartListResponseDto;
import com.sparta.shopapi.domain.cart.service.CartService;
import com.sparta.shopapi.global.common.ResponseDto;
import com.sparta.shopapi.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "CART API", description = "장바구니 관련 API")
public class CartController {
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "장바구니 상품 추가", description = "회원의 장바구니 상품 추가 API")
    @ApiResponse(responseCode = "201", description = "장바구니 상품 추가 성공", content = @Content(mediaType = "application/json"))
    public ResponseDto<CartResponseDto> addCart(@Valid @RequestBody CartRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) { // 상품 등록

        CartResponseDto responseDto = cartService.addCart(requestDto, userDetails.getUser());

        return ResponseDto.success("장바구니 정상 추가", responseDto);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "장바구니 조회", description = "회원의 장바구니 조회 API")
    @ApiResponse(responseCode = "200", description = "장바구니 조회 성공", content = @Content(mediaType = "application/json"))
    public ResponseDto<CartListResponseDto> getCartList(@AuthenticationPrincipal UserDetailsImpl userDetails) { // 상품 등록

        CartListResponseDto responseDto = cartService.getCartList(userDetails.getUser());

        return ResponseDto.success("장바구니 조회", responseDto);
    }

    @PutMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "장바구니 상품 수량 수정", description = "회원의 장바구니 상품 수량 수정 API")
    @ApiResponse(responseCode = "200", description = "장바구니 수량 수정 성공", content = @Content(mediaType = "application/json"))
    public ResponseDto<CartResponseDto> updateCart(@PathVariable Long cartId,
                                                   @RequestBody CartRequestDto requestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) { // 상품 등록

        CartResponseDto responseDto = cartService.updateCart(cartId,requestDto,userDetails.getUser());

        return ResponseDto.success("장바구니 상품수량 수정", responseDto);
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "장바구니 상품 삭제", description = "회원의 장바구니 상품 삭제 API")
    @ApiResponse(responseCode = "200", description = "장바구니 상품 삭제 성공", content = @Content(mediaType = "application/json"))
    public ResponseDto<CartResponseDto> deleteCart(@PathVariable Long cartId,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) { // 상품 삭제

        cartService.deleteCart(cartId,userDetails.getUser());

        return ResponseDto.success("장바구니에서 상품이 삭제 되었습니다." );
    }

}
