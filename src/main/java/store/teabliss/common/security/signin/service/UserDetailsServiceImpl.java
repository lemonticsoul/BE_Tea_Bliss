package store.teabliss.common.security.signin.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import store.teabliss.member.entity.Member;
import store.teabliss.member.entity.MemberDetails;
import store.teabliss.member.exception.NotFoundMemberByEmailException;
import store.teabliss.member.mapper.MemberMapper;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Member member = memberMapper.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberByEmailException(email));

        return MemberDetails.builder()
                .member(member)
                .build();
    }
}
