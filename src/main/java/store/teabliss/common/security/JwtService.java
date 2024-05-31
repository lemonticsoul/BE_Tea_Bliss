package store.teabliss.common.security;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public interface JwtService {

    String createAccessToken(String email);
    String createRefreshToken();

    void updateRefreshToken(String email,String refreshToken);

    void destroyRefreshToken(String email);

    void sendAccessAndRefreshToken(HttpServletResponse response,String accessToken,String refreshToken);

    Optional<String> extractAccessToken(HttpServletRequest request);

    Optional<String> extractRefrshToken(HttpServletRequest request);

    Optional<String> extractEmail(String accessToken);

    void setAccessTokenHeader(HttpServletResponse response,String accessToken);

    void setRefreshTokenHandler(HttpServletResponse response,String refreshToken);

    boolean isTokenValid(String token);


}
