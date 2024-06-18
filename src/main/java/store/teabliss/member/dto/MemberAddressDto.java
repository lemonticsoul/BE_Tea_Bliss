package store.teabliss.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import store.teabliss.member.entity.Member;

@Getter
public class MemberAddressDto {

    @Schema(description = "회원 주소", example = "주소 입력")
    private String address;

    @Builder
    public Member toEntity(Long memId) {
        return Member.builder()
                .memId(memId)
                .address(address)
                .build();
    }

}
