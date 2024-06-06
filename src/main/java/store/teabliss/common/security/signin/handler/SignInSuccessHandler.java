package store.teabliss.common.security.signin.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import store.teabliss.common.security.signin.service.JwtService;
import store.teabliss.member.dto.MemberResponse;
import store.teabliss.member.entity.Member;
import store.teabliss.member.exception.NotFoundMemberByEmailException;
import store.teabliss.member.mapper.MemberMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class SignInSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberMapper memberMapper;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String email = extractEmail(authentication);
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        Member member =  memberMapper.findByEmail(email).orElseThrow(
                () -> new NotFoundMemberByEmailException(email)
        );

        member.updateRefreshToken(refreshToken);

        memberMapper.updateMember(member);

        // log.info("로그인에 성공합니다. email:{}", email);
        // log.info("AccessToken을 발급합니다. AccessToken:{}", accessToken);
        // log.info("RefreshToken을 발급합니다. RefreshToken:{}", refreshToken);

        Map<String, Object> mem = new HashMap<>();
        mem.put("memId", member.getMemId());

        ObjectMapper objectMapper = new ObjectMapper();

        Cookie accessTokenCookie = new Cookie("accessToken",accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(24*60*60);

        Cookie refreshTokenCookie=new Cookie("refreshToken",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7*24*60*60);


        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), MemberResponse.ok(mem));

    }

    private String extractEmail(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
