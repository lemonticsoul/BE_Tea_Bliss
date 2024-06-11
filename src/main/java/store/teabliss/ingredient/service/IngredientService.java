package store.teabliss.ingredient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.ingredient.dto.FlavorResponseDto;
import store.teabliss.ingredient.dto.IngredientRequestDto;
import store.teabliss.ingredient.dto.IngredientResponseDto;
import store.teabliss.ingredient.entity.Flavor;
import store.teabliss.ingredient.entity.Ingredient;
import store.teabliss.ingredient.mapper.FlavorMapper;
import store.teabliss.ingredient.mapper.IngredientMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientMapper ingredientMapper;
    private final FlavorMapper flavorMapper;

    public Long createIngredient(IngredientRequestDto ingredientRequestDto) {
        Ingredient ingredient = Ingredient.builder()
                .category(ingredientRequestDto.getCategory())
                .name(ingredientRequestDto.getName())
                .nameEng(ingredientRequestDto.getNameEng())
                .flavor(ingredientRequestDto.getFlavor())
                .explanation(ingredientRequestDto.getExplanation())
                .photo(ingredientRequestDto.getPhoto())
                .build();

        return ingredientMapper.createIngredient(ingredient);
    }

    public List<IngredientResponseDto> findByIngredient(String category) {
        Ingredient ingredient = Ingredient.builder()
                .category(category)
                .build();

        List<Ingredient> ingredients = ingredientMapper.findByIngredients(ingredient);

        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();

        for(Ingredient i : ingredients) {

            IngredientResponseDto ingredientResponseDto;

            if(i.getFlavor() != null && !i.getFlavor().isEmpty()) {
                List<Flavor> flavorList = flavorMapper.findByFlavors(i.getFlavor().split(","));

                List<FlavorResponseDto> flavorResponseDtoList = FlavorResponseDto.ofs(flavorList);

                ingredientResponseDto = IngredientResponseDto.of(i, flavorResponseDtoList);
            } else {
                ingredientResponseDto = IngredientResponseDto.of(i);
            }

            ingredientResponseDtoList.add(ingredientResponseDto);
        }

        return ingredientResponseDtoList;
    }

    public int updateIngredient(IngredientRequestDto ingredientRequestDto) {
        Ingredient ingredient = Ingredient.builder()
                .id(ingredientRequestDto.getId())
                .category(ingredientRequestDto.getCategory())
                .name(ingredientRequestDto.getName())
                .nameEng(ingredientRequestDto.getNameEng())
                .flavor(ingredientRequestDto.getFlavor())
                .explanation(ingredientRequestDto.getExplanation())
                .photo(ingredientRequestDto.getPhoto())
                .build();

        return ingredientMapper.updateIngredient(ingredient);
    }

    public void deleteIngredient(Long id) {
        ingredientMapper.deleteIngredient(id);
    }

}
