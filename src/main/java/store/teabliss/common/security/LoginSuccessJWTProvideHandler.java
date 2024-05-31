package store.teabliss.common.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String email=extractEmail(authentication);
        String accessToken= jwtService.createAccessToken(email);
        String refreshToken=jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response,accessToken,refreshToken);
        userRepository.findByEmail(email).ifPresent(
                users->users.updateRefreshToken(refreshToken)
        );

        log.info("로그인에 성공합니다. email:{}",email);
        log.info("AccessToken을 발급합니다. AccessToken:{}",accessToken);
        log.info("RefreshToken을 발급합니다. RefreshToken:{}",refreshToken);

        response.getWriter().write("success");

    }

    private String extractEmail(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
