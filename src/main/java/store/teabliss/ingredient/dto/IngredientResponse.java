package store.teabliss.ingredient.dto;

import lombok.Builder;
import lombok.Getter;
import store.teabliss.common.response.CommonResponse;

@Getter
public class IngredientResponse extends CommonResponse {

    private final Object data;

    private final Integer total;

    @Builder
    public IngredientResponse(int status, String message, Object data, Integer total) {
        super(status, message);
        this.data = data;
        this.total = total;
    }

    public static IngredientResponse ok(String message) {
        return new IngredientResponse(200, message, null, null);
    }

    public static IngredientResponse ok(String message, Object data) {
        return new IngredientResponse(200, message, data, null);
    }


    public static IngredientResponse ok(String message, Object data, Integer total) {
        return new IngredientResponse(200, message, data, total);
    }
    public static IngredientResponse ok(Object data, Integer total) {
        return new IngredientResponse(200, "Success", data, total);
    }

    public static IngredientResponse ok(Object data) {
        return new IngredientResponse(200, "Success", data, null);
    }


}
