package store.teabliss.tea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaFlavor {

    private Long teaId;

    private Long  flavor;
}
