package com.sparta.shopapi.domain.product.controller;

import com.sparta.shopapi.domain.product.dto.RegisterProductRequestDto;
import com.sparta.shopapi.domain.product.dto.RegisterProductResponseDto;
import com.sparta.shopapi.domain.product.service.ProductService;
import com.sparta.shopapi.global.common.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "PRODUCT API", description = "상품 등록 및 조회 관련 API")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary ="상품 등록", description = "관리자의 상품 등록 API")
    @ApiResponse(responseCode = "201", description = "상품 등록 성공", content = @Content(mediaType = "application/json"))
    public ResponseDto<RegisterProductResponseDto> registerProduct(@Valid  @RequestBody RegisterProductRequestDto requestDto){ // 상품 등록
        RegisterProductResponseDto responseDto = productService.registerProduct(requestDto);
        return ResponseDto.success("상품이 정상적으로 등록되었습니다." ,responseDto);
    }

    // 선택한 상품의 상세 정보 조회
//    @GetMapping
//    public void readProduct(){
//
//    }
//
//    // 상품 전체 목록 조회
//    @GetMapping
//    public void readProductList(){
//
//    }
}
