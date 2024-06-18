package store.teabliss.review.dto;

import lombok.Builder;
import lombok.Getter;
import store.teabliss.common.response.CommonResponse;
import store.teabliss.ingredient.dto.IngredientResponse;

@Getter
public class ReviewResponse extends CommonResponse {

    private final Object data;
    private final boolean isLastPage;

    @Builder
    public ReviewResponse(int status, String message, Object data, boolean isLastPage) {
        super(status, message);
        this.data = data;
        this.isLastPage = isLastPage;
    }

    public static ReviewResponse ok(String message, Object data, boolean isLastPage) {
        return new ReviewResponse(200, message, data, isLastPage);
    }

    public static ReviewResponse ok(Object data, boolean isLastPage) {
        return new ReviewResponse(200, "Success", data, isLastPage);
    }

}
