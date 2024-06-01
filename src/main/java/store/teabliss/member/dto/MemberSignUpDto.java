package store.teabliss.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import store.teabliss.member.entity.Member;

@Getter
@ToString
public class MemberSignUpDto {

    @NotEmpty(message = "회원 이메일은 필수 입니다.")
    @Schema(description = "이메일 주소입니다.", example = "이메일 주소")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입니다.")
    @Schema(description = "비밀번호 입니다.", example = "비밀번호 입니다.")
    private String password;

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    @Schema(description = "회원의 이름 입니다", example = "회원1")
    private String name;

    @Email
    @Schema(description = "회원의 닉네임 입니다", example = "닉네임1")
    private String nickname;

    @Schema(description = "회원의 프로필 입니다", nullable = true, example = "사진파일1")
    private String profile;

    @Builder
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .profile(profile)
                .build();
    }

}
