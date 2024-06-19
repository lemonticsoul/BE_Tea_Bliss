package store.teabliss.tea.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeaPatchDto {

    private Long id ;

    private Long price;
    private String  category ;

    private String name ;
    private String nameEng;
    private boolean caffeine;
    private String description;
    private String img;
    private Long inventory ;

}
