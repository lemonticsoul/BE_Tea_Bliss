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

    @Builder
    public Member toEntity(Long memId) {
        return Member.builder()
                .memId(memId)
                .nickname(nickname)
                .profile(profile)
                .build();
    }

}
