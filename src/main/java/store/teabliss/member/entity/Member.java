package store.teabliss.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import store.teabliss.common.security.oauth2.user.OAuth2Provider;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long memId;

    private String email;

    @JsonIgnore
    private String password;

    private String nickname;

    private String address;

    private String profile;

    private MemberRole role;

    private String refreshToken;

    private OAuth2Provider provider;

    private String providerId;

    private int reviewCount;

    private double purchaseAmount;

    private int page;

    private int limit;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    public void passwordEncode(PasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void destroyRefreshToken() {
        this.refreshToken = null;
    }

    public Member socialUpdate(String nickname) {
        this.nickname = nickname;
        return this;
    }
}
