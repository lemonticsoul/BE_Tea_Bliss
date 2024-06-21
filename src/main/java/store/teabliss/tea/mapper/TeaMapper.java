package store.teabliss.tea.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.ingredient.entity.Flavor;
import store.teabliss.ingredient.entity.Ingredient;
import store.teabliss.tea.entity.Tea;
import store.teabliss.tea.entity.TeaFlavor;
import store.teabliss.tea.entity.TeaIngredient;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TeaMapper {


    void save(Tea tea);

    List<Tea> recommend();

    List<Tea> sale();

    List<Tea> topcost();

    List<Tea> lowcost();

    List<Tea> all();

    Long count();

    Tea findbyid(int id);

    List<Tea> category(String category);

    Long countByCategory(String category);

    Tea search(String product);

    void saveIngredient(TeaIngredient ingredient);
    void saveFlavor(TeaFlavor flavor);

    ArrayList<Long> findbyingredient(int id);

    ArrayList<Long> findbyflavor(int id);


    List<Tea> seasonsort(String season);

    List<Tea> caffeinesort(boolean caffeine);

    boolean deletetea(int id);
    boolean patchtea(Tea tea);

    void updateIngredient(TeaIngredient teaIngredient);

    void updateFlavor(TeaFlavor teaFlavor);



//    List<Tea> category();


}
