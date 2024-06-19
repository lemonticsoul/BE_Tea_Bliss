package store.teabliss.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import store.teabliss.member.dto.*;
import store.teabliss.member.entity.Member;
import store.teabliss.member.exception.DuplicationMemberEmailException;
import store.teabliss.member.exception.DuplicationNicknameException;
import store.teabliss.member.exception.NotEqualPasswordException;
import store.teabliss.member.exception.NotFoundMemberByIdException;
import store.teabliss.member.mapper.MemberMapper;

import java.util.List;

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

        return MemberDto.of1(member);
    }

    public int updateMember(Long memId, MemberEditDto memberEditDto) {
        Member member = memberMapper.findById(memId).orElseThrow(
                () -> new NotFoundMemberByIdException(memId)
        );

        Member updateMember = memberEditDto.toEntity(memId);

        return memberMapper.updateMember(updateMember);
    }

    public int updatePassword(Long memId, MemberPasswordDto memberPasswordDto) {
        Member member = memberMapper.findById(memId).orElseThrow(
                () -> new NotFoundMemberByIdException(memId)
        );

        if(!encoder.matches(memberPasswordDto.getOldPassword(), member.getPassword()))
            throw new NotEqualPasswordException("old");

        if(!memberPasswordDto.getNewPassword().equals(memberPasswordDto.getNewPasswordCheck()))
            throw new NotEqualPasswordException("new");

        Member updateMember = memberPasswordDto.toEntity(memId);

        updateMember.passwordEncode(encoder);

        return memberMapper.updatePassword(updateMember);
    }

    public int updateAddress(Long memId, MemberAddressDto memberAddressDto) {
        Member member = memberMapper.findById(memId).orElseThrow(
                () -> new NotFoundMemberByIdException(memId)
        );

        Member updateMember = memberAddressDto.toEntity(memId);

        return memberMapper.updateMember(updateMember);
    }

    /*
    관리자 영역
     */

    public MemberResponse memberList(String email, String nickname, int page, int limit) {
        Member searchMember = Member.builder()
                .email(email)
                .nickname(nickname)
                .page(page)
                .limit(limit)
                .build();

        List<Member> members = memberMapper.findByMembers(searchMember);
        Integer total = memberMapper.countByMembers(searchMember);

        return MemberResponse.ok(members, total);
    }

}
