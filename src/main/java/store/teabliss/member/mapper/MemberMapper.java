package store.teabliss.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    int createMember(Member member);

    Optional<Member> findByEmail(String member);

    Optional<Member> findByRefreshToken(String refreshToken);

    // List<Member> findByMembers();

    // Member save(Member member);


}
