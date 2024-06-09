package store.teabliss.common.security.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import store.teabliss.common.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import store.teabliss.common.security.oauth2.user.OAuth2Provider;
import store.teabliss.common.security.oauth2.user.OAuth2UserInfo;
import store.teabliss.common.security.oauth2.user.OAuth2UserInfoFactory;
import store.teabliss.member.entity.Member;
import store.teabliss.member.entity.MemberRole;
import store.teabliss.member.mapper.MemberMapper;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId,
                accessToken,
                oAuth2User.getAttributes());

        // OAuth2UserInfo field value validation
        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<Member> memberOptional = memberMapper.findByEmail(oAuth2UserInfo.getEmail());
        Member member;

        // 회원가입 여부
        if(memberOptional.isPresent()) {
            member = memberOptional.get();

            if(!member.getProvider().equals(OAuth2Provider.fromName(userRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException(member.getProvider() + "계정을 사용하기 위해서 로그인이 필요합니다.");
            }
            updateExisingMember(member, oAuth2UserInfo);

        } else {
            signUpNewMember(userRequest, oAuth2UserInfo);
        }

        return new OAuth2UserPrincipal(oAuth2UserInfo);
    }

    private void signUpNewMember(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
        memberMapper.createMember(
                Member.builder()
                        .email(oAuth2UserInfo.getEmail())
                        .password(encoder.encode("1111"))
                        .nickname(oAuth2UserInfo.getNickname())
                        .role(MemberRole.USER)
                        .provider(OAuth2Provider.fromName(userRequest.getClientRegistration().getRegistrationId()))
                        .providerId(oAuth2UserInfo.getId())
                        .build()
        );
    }

    private void updateExisingMember(Member existingMember, OAuth2UserInfo oAuth2UserInfo) {
        memberMapper.updateMember(existingMember.socialUpdate(oAuth2UserInfo.getNickname()));
    }
}