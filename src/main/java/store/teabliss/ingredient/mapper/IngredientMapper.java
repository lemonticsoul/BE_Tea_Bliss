package store.teabliss.ingredient.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.ingredient.entity.Ingredient;

import java.util.List;

@Mapper
public interface IngredientMapper {

    Long createIngredient(Ingredient ingredient);

    List<Ingredient> findByIngredients();

    int updateIngredient(Ingredient ingredient);

    void deleteIngredient(Long id);

}
