package store.teabliss.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import store.teabliss.member.entity.Member;
import store.teabliss.member.entity.MemberRole;

@Getter
@ToString
public class MemberSignUpDto {

    @Email
    @NotEmpty(message = "회원 이메일은 필수 입니다.")
    @Schema(description = "이메일 주소입니다.", example = "이메일 주소")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입니다.")
    @Schema(description = "비밀번호 입니다.", example = "비밀번호 입니다.")
    private String password;

    @Schema(description = "회원의 닉네임 입니다", example = "닉네임1")
    private String nickname;


    @Builder
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(MemberRole.USER)
                .build();
    }

}
