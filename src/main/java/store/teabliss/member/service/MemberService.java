package store.teabliss.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import store.teabliss.member.dto.MemberDto;
import store.teabliss.member.dto.MemberEditDto;
import store.teabliss.member.dto.MemberPasswordDto;
import store.teabliss.member.dto.MemberSignUpDto;
import store.teabliss.member.entity.Member;
import store.teabliss.member.exception.DuplicationMemberEmailException;
import store.teabliss.member.exception.DuplicationNicknameException;
import store.teabliss.member.exception.NotFoundMemberByIdException;
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

    public MemberDto selectMemberId(Long memId) {

        Member member = memberMapper.findById(memId).orElseThrow(
                () -> new NotFoundMemberByIdException(memId)
        );

        return MemberDto.of(member);
    }

    public int updateMember(Long memId, MemberEditDto memberEditDto) {
        Member member = memberEditDto.toEntity(memId);

        return memberMapper.updateMember(member);
    }

    public int updatePassword(Long memId, MemberPasswordDto memberPasswordDto) {
        Member member = memberMapper.findById(memId).orElseThrow(
                () -> new NotFoundMemberByIdException(memId)
        );

        if(!encoder.matches(memberPasswordDto.getOldPassword(), member.getPassword()))
            throw new RuntimeException("기존 비밀번호 입력이 틀립니다.");

        if(!memberPasswordDto.getNewPassword().equals(memberPasswordDto.getNewPasswordCheck()))
            throw new RuntimeException("새 비밀번호 비교가 틀립니다.");

        Member updateMember = memberPasswordDto.toEntity(memId);

        updateMember.passwordEncode(encoder);

        return memberMapper.updatePassword(member);
    }

}
