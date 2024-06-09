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

        Member member =  memberMapper.findByEmail(email).orElseThrow(
                () -> new NotFoundMemberByEmailException(email)
        );

        member.updateRefreshToken(refreshToken);

        memberMapper.updateMember(member);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        Map<String, Object> mem = new HashMap<>();
        mem.put("memId", member.getMemId());

        ObjectMapper objectMapper = new ObjectMapper();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), MemberResponse.ok(mem));

    }

    private String extractEmail(Authentication authentication){
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
