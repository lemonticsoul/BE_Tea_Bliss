package store.teabliss.common.security.oauth2.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Locale.ENGLISH;


@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    GITHUB("github"),
    NAVER("naver"),
    KAKAO("kakao");

    public static OAuth2Provider fromName(String type) { return OAuth2Provider.valueOf(type.toUpperCase(ENGLISH)); }

    public final String registrationId;
}
