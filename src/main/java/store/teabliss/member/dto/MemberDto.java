package store.teabliss.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import store.teabliss.member.entity.Member;
import store.teabliss.member.entity.MemberRole;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Builder
@Getter
@JsonInclude(Include.NON_NULL)
public class MemberDto {

    private Long id;

    private String email;

    private String nickname;

    private String address;

    private String profile;

    private String role;

    private int reviewCount;

    private double purchaseAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDt;

    public static MemberDto of1(Member member) {
        return MemberDto.builder()
                .id(member.getMemId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .address(member.getAddress())
                .profile(member.getProfile())
                .role(member.getRole().getKey().equalsIgnoreCase("USER") ? "일반 회원" : "관리자")
                .build();
    }

    public static MemberDto of2(Member member) {
        return MemberDto.builder()
                .id(member.getMemId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .profile(member.getProfile())
                .role(member.getRole().getKey().equalsIgnoreCase("USER") ? "일반 회원" : "관리자")
                .reviewCount(member.getReviewCount())
                .purchaseAmount(member.getPurchaseAmount())
                .createDt(member.getCreateDt())
                .build();
    }

    public static List<MemberDto> ofs2(List<Member> members) {
        return members.stream()
                .map(MemberDto::of2)
                .toList();
    }
}
