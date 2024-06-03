package store.teabliss.common.security;


import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import store.teabliss.member.entity.Member;
import store.teabliss.member.entity.MemberDetails;
import store.teabliss.member.mapper.MemberMapper;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final MemberMapper memberMapper;
    private final JwtService jwtService;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private static final String NO_CHECK_URL = "/api/member/sign-up";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtService
                .extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                    .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email -> memberMapper.findByEmail(email)
                                .ifPresent(this::saveAuthentication)
                        )
                );

        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(Member member) {
        MemberDetails memberDetails = MemberDetails.builder()
                .member(member)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, authoritiesMapper.mapAuthorities(memberDetails.getAuthorities()));

        // SecurityContext context = SecurityContextHolder.createEmptyContext();
        // context.setAuthentication(authentication);
        // SecurityContextHolder.setContext(context);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberMapper.findByRefreshToken(refreshToken).ifPresent(
                users -> jwtService.sendAccessToken(response, jwtService.createAccessToken(users.getEmail()))
        );
    }
}