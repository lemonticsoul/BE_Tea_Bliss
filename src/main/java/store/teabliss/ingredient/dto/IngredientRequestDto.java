package store.teabliss.ingredient.dto;

import lombok.Builder;
import lombok.Getter;
import store.teabliss.ingredient.entity.Ingredient;

@Getter
public class IngredientRequestDto {
    private Long id;

    private String category;

    private String name;

    private String nameEng;

    private String flavor;

    private String explanation;

    private String photo;
}
