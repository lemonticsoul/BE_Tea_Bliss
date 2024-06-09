package store.teabliss.ingredient.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IngredientRequestDto {
    private Long id;

    private String category;

    private String name;

    private String explanation;
}
