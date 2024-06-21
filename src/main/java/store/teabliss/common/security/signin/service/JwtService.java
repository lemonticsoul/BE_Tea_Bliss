package store.teabliss.common.security.signin.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import store.teabliss.common.entity.ErrorMessage;
import store.teabliss.common.security.utils.CookieUtils;
import store.teabliss.member.entity.Member;
import store.teabliss.member.mapper.MemberMapper;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private static final long ACCESS_TOKEN_EXPIRED_TIME = 1000L * 60L * 60L * 24L; // 1일
    private static final long REFRESH_TOKEN_EXPIRED_TIME = 1000L; // 7일
    private static final String ACCESS_TOKEN = "Authorization";
    private static final String REFRESH_TOKEN = "RefreshToken";
    private static final String BEARER = "Bearer ";

    private final MemberMapper memberMapper;
    private final ObjectMapper objectMapper;

    private Key getSecretKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String email) {
        Date date = new Date();

        Claims claims = Jwts.claims()
                .setSubject(ACCESS_TOKEN)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRED_TIME));

        claims.put("email", email);

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(claims)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken() {
        Date date = new Date();

        Claims claims = Jwts.claims()
                .setSubject(REFRESH_TOKEN)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_EXPIRED_TIME));

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(claims)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ","JWT");
        header.put("alg","HS512");
        return header;
    }

    public void updateRefreshToken(String email, String refreshToken) {
        memberMapper.findByEmail(email)
                .ifPresentOrElse(
                        member -> member.updateRefreshToken(refreshToken),
                        () -> { throw new RuntimeException("회원 조회 실패"); }
                );
    }

    public void destroyRefreshToken(String email) {
        memberMapper.findByEmail(email)
                .ifPresentOrElse(
                        Member::destroyRefreshToken,
                        () -> { throw new RuntimeException("회원 조회 실패"); }
                );
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        // CookieUtils.addCookie(response, "accessToken", accessToken, 24 * 60 * 60);
        setAccessTokenHeader(response, accessToken);
        CookieUtils.addCookie(response, "refreshToken", refreshToken, 7 * 24 * 60 * 60);
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        // CookieUtils.addCookie(response, "accessToken", accessToken, 24 * 60 * 60);
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(ACCESS_TOKEN, BEARER + accessToken);
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(ACCESS_TOKEN))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        Cookie refreshToken = WebUtils.getCookie(request, "refreshToken");

        if(refreshToken == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(refreshToken.getValue());
    }

    public Optional<String> extractEmail(String accessToken) {
        try {
            return Optional.ofNullable(Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody().get("email", String.class));
        } catch (Exception e) {
            log.error("유효하지 않은 AccessToken입니다", e);
            return Optional.empty();
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (MalformedJwtException e) {
            log.info("MalformedJwtException");
            throw new JwtException(ErrorMessage.UNSUPPORTED_TOKEN.getMsg());
        } catch (ExpiredJwtException e) {
            log.info("ExpiredJwtException");
            throw new JwtException(ErrorMessage.EXPIRED_TOKEN.getMsg());
        } catch (IllegalArgumentException e) {
            log.info("IllegalArgumentException");
            throw new JwtException(ErrorMessage.UNKNOWN_ERROR.getMsg());
        }
    }
}

