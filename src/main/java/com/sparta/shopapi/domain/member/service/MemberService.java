package com.sparta.shopapi.domain.member.service;

import com.sparta.shopapi.domain.member.dto.SignUpRequestDto;
import com.sparta.shopapi.domain.member.dto.SignUpResponseDto;
import com.sparta.shopapi.domain.member.entity.Member;
import com.sparta.shopapi.domain.member.entity.MemberRole;
import com.sparta.shopapi.domain.member.repository.MemberRepository;
import com.sparta.shopapi.global.handler.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sparta.shopapi.global.handler.exception.ErrorCode.ADMIN_TOKEN_MISMATCH;
import static com.sparta.shopapi.global.handler.exception.ErrorCode.EMAIL_DUPLICATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN 일반사용자인지 관리자인지 구분하기 위한 토큰
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        //비밀번호 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        //이메일 중복 확인
        String email = requestDto.getEmail();
        Optional<Member> checkEmail = memberRepository.findByEmail(email);
        if(checkEmail.isPresent()){
            throw new CustomApiException(EMAIL_DUPLICATION.getMessage());
        }

        // 사용자 권한 확인
        MemberRole role = MemberRole.USER;
        if(requestDto.isAdmin()){
            if(!ADMIN_TOKEN.equals(requestDto.getAdminToken())){
                // 관리자 암호 불일치
                throw new CustomApiException(ADMIN_TOKEN_MISMATCH.getMessage());
            }
            role = MemberRole.ADMIN;
        }

        Member member = memberRepository.save(requestDto.toEntity(role,password));
        // 사용자 등록, 권한이랑 암호화한 비밀번호 보내서 저장
        return new SignUpResponseDto(member);
    }
}
