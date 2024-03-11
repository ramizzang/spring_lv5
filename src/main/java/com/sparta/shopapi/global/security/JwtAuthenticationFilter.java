package com.sparta.shopapi.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.shopapi.domain.member.dto.LoginRequestDto;
import com.sparta.shopapi.domain.member.entity.MemberRole;
import com.sparta.shopapi.global.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Slf4j(topic = "로그인 & JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/member/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        // 사용자에게 입력 받은 값을 getAuthenticationManager 에게 넘겨줘서 검증하기
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(), // 이메일 정보
                            requestDto.getPassword(), // 비밀번호 정보
                            null // 권한
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        MemberRole role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(email, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        response.setStatus(HttpServletResponse.SC_OK); // 200 상태 코드 설정
        response.setContentType("application/json"); // 응답의 컨텐트 타입을 JSON으로 설정
        response.setCharacterEncoding("UTF-8"); // 문자 인코딩 설정

        // 성공 메시지를 JSON 형태로 작성
        String successMessage = "{\"success\": true, \"message\": \"로그인 성공\"}";

        try {
            response.getWriter().write(successMessage); // 응답에 성공 메시지 작성
        } catch (IOException e) {
            log.error("응답 메시지 작성 중 오류 발생", e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        throw failed;

    }


}
