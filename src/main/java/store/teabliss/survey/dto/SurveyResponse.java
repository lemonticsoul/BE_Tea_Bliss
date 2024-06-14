package store.teabliss.survey.dto;

import lombok.Builder;
import store.teabliss.common.response.CommonResponse;
import store.teabliss.ingredient.dto.IngredientResponse;

public class SurveyResponse extends CommonResponse {

    private final Object data;

    @Builder
    public SurveyResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static SurveyResponse ok(String message, Object data) {
        return new SurveyResponse(200, message, data);
    }

    public static SurveyResponse ok(Object data) {
        return new SurveyResponse(200, "Success", data);
    }
}
