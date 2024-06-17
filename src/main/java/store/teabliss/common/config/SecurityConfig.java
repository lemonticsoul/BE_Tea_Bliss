package store.teabliss.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import store.teabliss.common.security.oauth2.handler.OAuth2AuthenticationFailureHandler;
import store.teabliss.common.security.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import store.teabliss.common.security.signin.UsernamePasswordAuthenticationFilter;
import store.teabliss.common.security.signin.handler.SignInFailureHandler;
import store.teabliss.common.security.signin.handler.SignInSuccessHandler;
import store.teabliss.common.security.signin.JwtAuthorizationFilter;
import store.teabliss.common.security.signin.service.JwtService;
import store.teabliss.common.security.signin.service.UserDetailsServiceImpl;
import store.teabliss.common.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import store.teabliss.common.security.oauth2.service.CustomOAuth2UserService;
import store.teabliss.member.mapper.MemberMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberMapper memberMapper;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailService;
    private final ObjectMapper objectMapper;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    // Swagger URL
    private final String[] permitUrl = new String[]{"/swagger", "/swagger-ui.html", "/swagger-ui/**"
            , "/api-docs", "/api-docs/**", "/v3/api-docs/**", "/uploadImage/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                // error endpoint를 열어줘야 함, favicon.ico 추가!
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public CorsConfiguration corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000", "http://localhost:3001"
                , "https://localhost:3001"
        ));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(List.of("Authorization", "Set-Cookie"));
        config.setMaxAge(3600L);
        return config;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .headers(c -> c.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((request) -> {
                        // request.requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll();
                        request.requestMatchers(antMatcher("/api/member/**")).permitAll();
                        request.requestMatchers(antMatcher("/api/ingredient/**")).permitAll();
                        request.requestMatchers(antMatcher(HttpMethod.GET, "/api/tea/**")).permitAll();
                        // request.requestMatchers(new AntPathRequestMatcher("/api/survey/**")).authenticated();
                        request.requestMatchers(permitUrl).permitAll();
                        request.anyRequest().authenticated();
                    }
                );

        http.
                oauth2Login(configure ->
                        configure.authorizationEndpoint(config -> config.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                                .userInfoEndpoint(config -> config.userService(customOAuth2UserService))
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                .failureHandler(oAuth2AuthenticationFailureHandler)
                );

        http
                .addFilterAfter(usernamePasswordAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
        return delegatingPasswordEncoder;
    }

    @Bean
    public SignInSuccessHandler signInSuccessHandler(){
        return new SignInSuccessHandler(memberMapper, jwtService);
    }

    @Bean
    public SignInFailureHandler signInFailureHandler(){
        return new SignInFailureHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }

    @Bean
    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception{
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter(objectMapper);
        usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(signInSuccessHandler());
        usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(signInFailureHandler());
        return usernamePasswordAuthenticationFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthenticationProcessingFilter(){
        return new JwtAuthorizationFilter(memberMapper, jwtService);
    }

}
