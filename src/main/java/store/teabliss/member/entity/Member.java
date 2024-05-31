package store.teabliss.member.entity;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {


    private Long memId;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String profile;

    private MemberRole role;

    private String refreshToken;

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void destroyRefreshToken() {
        this.refreshToken = null;
    }

}
