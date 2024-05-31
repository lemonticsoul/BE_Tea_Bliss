package store.teabliss.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.member.entity.Member;
import store.teabliss.member.exception.DuplicationMemberEmailException;
import store.teabliss.member.mapper.MemberMapper;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public int createMember(Member member) {

        if(memberMapper.findByEmail(member).isEmpty()) {
            throw new DuplicationMemberEmailException(member.getEmail());
        }

        return memberMapper.createMember(member);
    }

}
