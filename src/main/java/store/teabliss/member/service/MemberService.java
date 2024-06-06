package store.teabliss.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.teabliss.member.dto.MemberSignUpDto;
import store.teabliss.member.entity.Member;
import store.teabliss.member.exception.DuplicationMemberEmailException;
import store.teabliss.member.exception.DuplicationNicknameException;
import store.teabliss.member.mapper.MemberMapper;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    public long createMember(MemberSignUpDto memberSignUpDto) {

        // 이메일 중복
        if(memberMapper.existsByEmail(memberSignUpDto.getEmail()))
            throw new DuplicationMemberEmailException(memberSignUpDto.getEmail());
        
        // 닉네임 중복
        if(memberMapper.existsByNickname(memberSignUpDto.getNickname()))
            throw new DuplicationNicknameException(memberSignUpDto.getNickname());
        

        Member member = memberSignUpDto.toEntity();
        member.passwordEncode(encoder);

        memberMapper.createMember(member);

        return member.getMemId();
    }

}
