package store.teabliss.ingredient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.ingredient.dto.FlavorResponseDto;
import store.teabliss.ingredient.dto.IngredientResponseDto;
import store.teabliss.ingredient.entity.Flavor;
import store.teabliss.ingredient.entity.Ingredient;
import store.teabliss.ingredient.mapper.FlavorMapper;
import store.teabliss.ingredient.mapper.IngredientMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientMapper ingredientMapper;
    private final FlavorMapper flavorMapper;

    public List<IngredientResponseDto> findByIngredient() {
        List<Ingredient> ingredients = ingredientMapper.findByIngredients();

        List<IngredientResponseDto> ingredientResponseDtoList = new ArrayList<>();

        for(Ingredient i : ingredients) {

            // long[] flavor_ids = Arrays.stream(i.getFlavor().split(",")).mapToLong(Long::parseLong).toArray();

            List<Flavor> flavorList = flavorMapper.findByFlavors(i.getFlavor().split(","));

            List<FlavorResponseDto> flavorResponseDtoList = FlavorResponseDto.ofs(flavorList);

            // for(long f_id : flavor_ids) {
            //     Flavor flavor = flavorMapper.findByFlavor(f_id).orElseThrow();
            //
            //     FlavorResponseDto flavorResponseDto = FlavorResponseDto.of(flavor);
            //
            //     flavorResponseDtoList.add(flavorResponseDto);
            // }

            IngredientResponseDto ingredientResponseDto = IngredientResponseDto.of(i, flavorResponseDtoList);

            ingredientResponseDtoList.add(ingredientResponseDto);
        }

        return ingredientResponseDtoList;
    }

}
