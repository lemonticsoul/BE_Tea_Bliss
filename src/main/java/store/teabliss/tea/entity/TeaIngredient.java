package store.teabliss.tea.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaIngredient {

    private Long teaId;

    private Long ingredientId;
}
