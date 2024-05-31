package store.teabliss.common.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import store.teabliss.member.mapper.MemberMapper;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SignInSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberMapper memberMapper;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String email=extractEmail(authentication);
        String accessToken= jwtService.createAccessToken(email);
        String refreshToken=jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response,accessToken,refreshToken);
        memberMapper.findByEmail(email).ifPresent(
                member -> member.updateRefreshToken(refreshToken)
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
