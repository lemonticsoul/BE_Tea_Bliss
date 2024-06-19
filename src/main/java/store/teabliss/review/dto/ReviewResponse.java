package store.teabliss.review.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;
import store.teabliss.common.response.CommonResponse;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@JsonInclude(Include.NON_NULL)
public class ReviewResponse extends CommonResponse {

    private final Object data;

    private final Boolean isLastPage;

    private final Integer total;

    @Builder
    public ReviewResponse(int status, String message, Object data, Boolean isLastPage, Integer total) {
        super(status, message);
        this.data = data;
        this.isLastPage = isLastPage;
        this.total = total;
    }

    public static ReviewResponse ok(String message, Object data, Boolean isLastPage, Integer total) {
        return new ReviewResponse(200, message, data, isLastPage, total);
    }

    public static ReviewResponse ok(Object data, Boolean isLastPage, Integer total) {
        return new ReviewResponse(200, "Success", data, isLastPage, total);
    }

    public static ReviewResponse ok(Object data) {
        return new ReviewResponse(200, "Success", data, null, null);
    }

}
