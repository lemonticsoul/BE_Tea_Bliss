package store.teabliss.ingredient.dto;

import lombok.Builder;
import lombok.Getter;
import store.teabliss.ingredient.entity.Ingredient;

import java.util.List;

@Builder
@Getter
public class IngredientResponseDto {

    private Long id;

    private String category;

    private String name;

    private String nameEng;

    private int sale;

    private int inventory;

    private String saleStatus;

    private List<FlavorResponseDto> flavors;

    private String explanation;

    private String photo;

    public static IngredientResponseDto of(Ingredient ingredient) {
        return IngredientResponseDto.builder()
                .id(ingredient.getId())
                .category(ingredient.getCategory())
                .name(ingredient.getName())
                .nameEng(ingredient.getNameEng())
                .sale(ingredient.getSale())
                .inventory(ingredient.getInventory())
                .saleStatus(ingredient.getSaleStatus())
                .explanation(ingredient.getExplanation())
                .photo(ingredient.getPhoto())
                .build();
    }

    public static IngredientResponseDto of(Ingredient ingredient, List<FlavorResponseDto> flavors) {
        return IngredientResponseDto.builder()
                .id(ingredient.getId())
                .category(ingredient.getCategory())
                .name(ingredient.getName())
                .nameEng(ingredient.getNameEng())
                .sale(ingredient.getSale())
                .inventory(ingredient.getInventory())
                .saleStatus(ingredient.getSaleStatus())
                .flavors(flavors)
                .explanation(ingredient.getExplanation())
                .photo(ingredient.getPhoto())
                .build();
    }

}
