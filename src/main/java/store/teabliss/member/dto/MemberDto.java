package store.teabliss.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import store.teabliss.member.entity.Member;

@Builder
@Getter
public class MemberDto {

    private String email;

    private String nickname;

    private String address;

    private String profile;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .address(member.getAddress())
                .profile(member.getProfile())
                .build();
    }
}
