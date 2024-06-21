package store.teabliss.tea.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

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

    private ArrayList<Long> ingredient;

    private ArrayList<Long> flavor;

    private String saleStatus;

    private String season;

}
