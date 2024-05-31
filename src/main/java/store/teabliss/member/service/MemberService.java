package store.teabliss.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.teabliss.member.dto.MemberSignUpDto;
import store.teabliss.member.entity.Member;
import store.teabliss.member.exception.DuplicationMemberEmailException;
import store.teabliss.member.mapper.MemberMapper;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    public int createMember(MemberSignUpDto memberSignUpDto) {

        if(memberMapper.existsByEmail(memberSignUpDto.getEmail())) {
            throw new DuplicationMemberEmailException(memberSignUpDto.getEmail());
        }

        Member member = memberSignUpDto.toEntity();
        member.passwordEncode(encoder);

        return memberMapper.createMember(member);
    }

}
