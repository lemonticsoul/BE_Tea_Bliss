package store.teabliss.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import store.teabliss.member.entity.Member;
import store.teabliss.member.entity.MemberRole;

@Getter
public class MemberEditDto {

    @Schema(description = "닉네임", example = "nickname")
    private String nickname;

    @Schema(description = "프로필 사진 URL", example = "~~")
    private String profile;

    @Schema(description = "회원 권한", example = "user")
    private String role;

    @Builder
    public Member toEntity(Long memId) {
        return Member.builder()
                .memId(memId)
                .nickname(nickname)
                .profile(profile)
                .role(role.equalsIgnoreCase("user") ? MemberRole.USER : MemberRole.ADMIN)
                .build();
    }

}
