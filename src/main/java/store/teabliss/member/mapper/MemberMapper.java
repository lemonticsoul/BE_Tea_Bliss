package store.teabliss.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    int createMember(Member member);

    // List<Member> findByMembers();

    // Member save(Member member);

    // Optional<Member> findByEmail(String email);

}
