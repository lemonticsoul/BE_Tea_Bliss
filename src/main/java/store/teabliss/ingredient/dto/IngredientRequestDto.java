package store.teabliss.ingredient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import store.teabliss.ingredient.entity.Ingredient;

@Getter
@ToString
public class IngredientRequestDto {
    private Long id;

    private String category;

    private String name;

    private String nameEng;

    private int sale;

    private int inventory;

    private String saleStatus;

    private String flavor;

    private String explanation;

    private String photo;
}
