package com.sparta.shopapi.domain.member.controller;

import com.sparta.shopapi.domain.member.dto.SignUpRequestDto;
import com.sparta.shopapi.domain.member.dto.SignUpResponseDto;
import com.sparta.shopapi.domain.member.service.MemberService;
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
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "MEMBER API", description = "회원가입 관련 API")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary ="사용자 회원가입", description = "사용자 회원가입 API")
    @ApiResponse(responseCode = "201", description = "회원 가입 성공", content = @Content(mediaType = "application/json"))
    public ResponseDto<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto){
        return ResponseDto.success("회원가입이 완료되었습니다." ,memberService.signUp(requestDto));
    }


}
