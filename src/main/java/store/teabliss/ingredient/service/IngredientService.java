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

    public List<IngredientResponseDto> findByIngredients(int page, int limit, String category) {

        int pagination = limit * (page - 1);

        Ingredient ingredient = Ingredient.builder()
                .category(category)
                .page(pagination)
                .limit(limit)
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

    public IngredientResponseDto findByIngredient(Long id) {

        Ingredient i = ingredientMapper.findById(id);

        IngredientResponseDto ingredientResponseDto;

        if(i.getFlavor() != null && !i.getFlavor().isEmpty()) {
            List<Flavor> flavorList = flavorMapper.findByFlavors(i.getFlavor().split(","));

            List<FlavorResponseDto> flavorResponseDtoList = FlavorResponseDto.ofs(flavorList);

            ingredientResponseDto = IngredientResponseDto.of(i, flavorResponseDtoList);
        } else {
            ingredientResponseDto = IngredientResponseDto.of(i);
        }

        return ingredientResponseDto;
    }

    public int updateIngredient(IngredientRequestDto ingredientRequestDto) {
        Ingredient ingredient = Ingredient.builder()
                .id(ingredientRequestDto.getId())
                .category(ingredientRequestDto.getCategory())
                .name(ingredientRequestDto.getName())
                .nameEng(ingredientRequestDto.getNameEng())
                .sale(ingredientRequestDto.getSale())
                .inventory(ingredientRequestDto.getInventory())
                .saleStatus(ingredientRequestDto.getSaleStatus())
                .flavor(ingredientRequestDto.getFlavor())
                .explanation(ingredientRequestDto.getExplanation())
                .photo(ingredientRequestDto.getPhoto())
                .build();

        return ingredientMapper.updateIngredient(ingredient);
    }

    public int updateIngredients(List<IngredientRequestDto> ingredientRequestDtos) {

        int count = 0;

        for(IngredientRequestDto iDto : ingredientRequestDtos) {
            Ingredient ingredient = Ingredient.builder()
                    .id(iDto.getId())
                    .category(iDto.getCategory())
                    .name(iDto.getName())
                    .nameEng(iDto.getNameEng())
                    .sale(iDto.getSale())
                    .inventory(iDto.getInventory())
                    .saleStatus(iDto.getSaleStatus())
                    .flavor(iDto.getFlavor())
                    .explanation(iDto.getExplanation())
                    .photo(iDto.getPhoto())
                    .build();

            int success = ingredientMapper.updateIngredient(ingredient);

            count += success;
        }

        return count;
    }

    public void deleteIngredient(Long id) {
        ingredientMapper.deleteIngredient(id);
    }

}
