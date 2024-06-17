package store.teabliss.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    void createMember(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<Member> findByRefreshToken(String refreshToken);

    List<Member> findByMembers();

    int updateMember(Member member);

    int updatePassword(Member member);

    void updateRefreshToken(Member member);

    void deleteMember(String email);


}
