package store.teabliss.ingredient.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

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
