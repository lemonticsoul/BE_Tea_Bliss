package store.teabliss.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import store.teabliss.member.entity.Member;
import store.teabliss.member.entity.MemberRole;

@Getter
public class MemberEditDto {

    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @Schema(description = "이메일 주소", example = "email@email.com")
    private String email;

    @Schema(description = "닉네임", example = "nickname")
    private String nickname;

    @Schema(description = "프로필 사진 URL", example = "~~")
    private String profile;

    @Builder
    public Member toEntity(Long memId) {
        return Member.builder()
                .memId(memId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .profile(profile)
                .build();
    }

}
