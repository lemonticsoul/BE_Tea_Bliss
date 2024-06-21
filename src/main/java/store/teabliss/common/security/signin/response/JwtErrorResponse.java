package store.teabliss.common.security.signin.response;

import lombok.Getter;
import store.teabliss.common.response.CommonResponse;

@Getter
public class JwtErrorResponse extends CommonResponse {

    private final String code;

    public JwtErrorResponse(int status, String code, String message) {
        super(status, message);
        this.code = code;
    }

    public static JwtErrorResponse error(String code, String message) {
        return new JwtErrorResponse(400, code, message);
    }
}
