package store.teabliss.member.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public void passwordEncode(PasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void destroyRefreshToken() {
        this.refreshToken = null;
    }

}
