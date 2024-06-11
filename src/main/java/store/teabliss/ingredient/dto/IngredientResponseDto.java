package store.teabliss.ingredient.dto;

import lombok.Builder;
import lombok.Getter;
import store.teabliss.ingredient.entity.Ingredient;

import java.util.List;

@Builder
@Getter
public class IngredientResponseDto {

    private String category;

    private String name;

    private String nameEng;

    private List<FlavorResponseDto> flavors;

    private String explanation;

    private String photo;

    public static IngredientResponseDto of(Ingredient ingredient) {
        return IngredientResponseDto.builder()
                .category(ingredient.getCategory())
                .name(ingredient.getName())
                .nameEng(ingredient.getNameEng())
                .explanation(ingredient.getExplanation())
                .photo(ingredient.getPhoto())
                .build();
    }

    public static IngredientResponseDto of(Ingredient ingredient, List<FlavorResponseDto> flavors) {
        return IngredientResponseDto.builder()
                .category(ingredient.getCategory())
                .name(ingredient.getName())
                .nameEng(ingredient.getNameEng())
                .flavors(flavors)
                .explanation(ingredient.getExplanation())
                .photo(ingredient.getPhoto())
                .build();
    }

}
