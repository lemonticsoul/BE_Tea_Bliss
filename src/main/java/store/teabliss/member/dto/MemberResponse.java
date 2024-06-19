package store.teabliss.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import store.teabliss.common.response.CommonResponse;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@JsonInclude(Include.NON_NULL)
public class MemberResponse extends CommonResponse {

    private final Object data;

    private final Integer total;

    @Builder
    public MemberResponse(int status, String message, Object data, Integer total) {
        super(status, message);
        this.data = data;
        this.total = total;
    }

    public static MemberResponse ok(Object member) {
        return new MemberResponse(200, "Success", member, null);
    }

    public static MemberResponse ok(Object member, Integer total) {
        return new MemberResponse(200, "Success", member, total);
    }

    public static MemberResponse profileError() {
        return new MemberResponse(400, "해당 프로필 파일은 10MB를 넘기면 안됩니다.", null, null);
    }
}
