package com.sparta.shopapi.domain.member.dto;

import com.sparta.shopapi.domain.member.entity.Gender;
import com.sparta.shopapi.domain.member.entity.Member;
import com.sparta.shopapi.domain.member.entity.MemberRole;
import lombok.Getter;

@Getter
public class SignUpResponseDto {
    private Long id;
    private String email;
    private Gender gender;
    private String phone;
    private String address;
    private MemberRole role;


    public SignUpResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.gender = member.getGender();
        this.phone = member.getPhone();
        this.address = member.getAddress();
        this.role = member.getRole();
    }
}

