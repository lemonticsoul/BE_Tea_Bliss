package store.teabliss.tea.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeaIngredient {

    private Long teaId;

    private Long ingredientId;
}
