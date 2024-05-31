package store.teabliss.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse extends CommonResponse {

    @Builder
    public ErrorResponse(int status, String message) {
        super(status, message);
    }

}
