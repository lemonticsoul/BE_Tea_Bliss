package store.teabliss.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.member.entity.Member;
import store.teabliss.member.mapper.MemberMapper;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public int createMember(Member member) {
        return memberMapper.createMember(member);
    }

}
