package com.sparta.shopapi.domain.member.dto;

import com.sparta.shopapi.domain.member.entity.Gender;
import com.sparta.shopapi.domain.member.entity.Member;
import com.sparta.shopapi.domain.member.entity.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Schema(description = "Member join request")
public class SignUpRequestDto {

    @Email(message = "이메일을 입력해주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Schema(description = "이메일", example = "admin@email.com")
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "비밀번호는 8~15, 숫자, 영어 대소문자, 특수문자@$!%*?& 가 포함되야 합니다")
    @Schema(description = "비밀번호", example = "p@ssw0rd!")
    private String password;

    @NotNull(message = "성별을 선택하세요")
    @Schema(description = "성별", example = "FEMALE")
    private Gender gender;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}$")
    @NotBlank(message = "전화번호를 입력해주세요. 010-1234-5678 형식으로 입력해주세요")
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;

    @NotBlank(message = "주소를 입력해주세요.")
    @Schema(description = "주소", example = "경기도 용인시 기흥구")
    private String address;

    @Schema(description = "관리자 여부 확인", example = "true")
    private boolean admin = false; // 관리자 확인

    @Schema(description = "관리자 확인 토큰", example = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC")
    private String adminToken = ""; // 관리자일 때 토큰


    @Builder
    public SignUpRequestDto(String email, String password, Gender gender, String phone, String address, boolean admin) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.admin = admin;
    }

    public Member toEntity(MemberRole role, String password){
        return Member.builder()
                .email(email)
                .password(password)
                .gender(gender)
                .phone(phone)
                .address(address)
                .role(role)
                .build();
    }
}
