package store.teabliss.member.dto;

import lombok.Builder;
import lombok.Getter;
import store.teabliss.member.entity.Member;

@Getter
public class MemberPasswordDto {

    private String oldPassword;

    private String newPassword;

    private String newPasswordCheck;

    @Builder
    public Member toEntity(Long memId) {
        return Member.builder()
                .memId(memId)
                .password(newPassword)
                .build();
    }

}
