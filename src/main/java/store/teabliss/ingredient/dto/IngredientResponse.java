package store.teabliss.ingredient.dto;

import lombok.Builder;
import lombok.Getter;
import store.teabliss.common.response.CommonResponse;

@Getter
public class IngredientResponse extends CommonResponse {

    private final Object data;

    @Builder
    public IngredientResponse(int status, String message, Object data) {
        super(status, message);
        this.data = data;
    }

    public static IngredientResponse ok(String message, Object data) {
        return new IngredientResponse(200, message, data);
    }

    public static IngredientResponse ok(Object data) {
        return new IngredientResponse(200, "Success", data);
    }



}
