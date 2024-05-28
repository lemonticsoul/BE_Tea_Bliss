package store.teabliss.member.dto;

import lombok.*;
import store.teabliss.member.entity.Member;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberSignUpDto {

    private String email;

    private String password;

    private String name;

    private String nickname;

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
