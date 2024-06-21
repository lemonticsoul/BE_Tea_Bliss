package store.teabliss.ingredient.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class IngredientCreateDto {

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
