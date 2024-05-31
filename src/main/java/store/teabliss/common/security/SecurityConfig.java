package store.teabliss.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import store.teabliss.member.mapper.MemberMapper;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // private final CustomOAuth2UserService oAuth2UserService;
    // private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final MemberMapper memberMapper;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailService;
    private final ObjectMapper objectMapper;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                // error endpoint를 열어줘야 함, favicon.ico 추가!
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .headers(c -> c.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((request) -> {
                        request.requestMatchers(new AntPathRequestMatcher("/api/v1/**","/api/login/**")).permitAll();
                        request.requestMatchers(PathRequest.toH2Console()).permitAll();
                    }
                );
        http
                .addFilterAfter(usernamePasswordAuthenticationFilter(),LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public SignInSuccessHandler loginSuccessJWTProvideHandler(){
        return new SignInSuccessHandler(memberMapper, jwtService);
    }

    @Bean
    public SignInFailureHandler loginFailureHandler(){
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
        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
        usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return usernamePasswordAuthenticationFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthenticationProcessingFilter(){
        return new JwtAuthorizationFilter(memberMapper, jwtService);
    }

}
