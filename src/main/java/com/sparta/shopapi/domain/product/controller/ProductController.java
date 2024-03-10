package com.sparta.shopapi.domain.product.controller;

import com.sparta.shopapi.domain.product.dto.ProductListResponseDto;
import com.sparta.shopapi.domain.product.dto.RegisterProductRequestDto;
import com.sparta.shopapi.domain.product.dto.ProductResponseDto;
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

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "PRODUCT API", description = "상품 등록 및 조회 관련 API")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "상품 등록", description = "관리자의 상품 등록 API")
    @ApiResponse(responseCode = "201", description = "상품 등록 성공", content = @Content(mediaType = "application/json"))
    public ResponseDto<ProductResponseDto> registerProduct(@Valid @RequestBody RegisterProductRequestDto requestDto) { // 상품 등록
        ProductResponseDto responseDto = productService.registerProduct(requestDto);
        return ResponseDto.success("상품 정상 등록", responseDto);
    }

    //     선택한 상품의 상세 정보 조회
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "선택한 상품 조회", description = "선택한 상품 조회 API")
    @ApiResponse(responseCode = "200", description = "선택 상품 조회 성공", content = @Content(mediaType = "application/json"))
    public ResponseDto<ProductResponseDto> readProduct(@PathVariable Long productId) {
        ProductResponseDto responseDto = productService.readProduct(productId);
        return ResponseDto.success("선택한 상품 정상 조회", responseDto);
    }


    // 상품 전체 목록 조회, 페이징, (상품명, 가격) 내림차순, 오름차순
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "상품 목록 조회", description = "상품 목록 조회 API")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공", content = @Content(mediaType = "application/json"))
    public ResponseDto<ProductListResponseDto> readProductList(
            @RequestParam(value = "page",required = false,defaultValue = "1") int page,
            @RequestParam(value = "size", required = false,defaultValue = "5") int size,
            @RequestParam(value = "sortBy",required = false,defaultValue = "productName") String sortBy,
            @RequestParam(value = "isAsc",required = false,defaultValue = "true") boolean isAsc
    ) {
       ProductListResponseDto responseList = productService.readProductList(page-1,size,sortBy,isAsc);
        return ResponseDto.success("상품 목록 정상 조회", responseList);
    }


}
