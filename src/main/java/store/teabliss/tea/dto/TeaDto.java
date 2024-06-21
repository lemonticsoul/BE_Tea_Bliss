package store.teabliss.tea.dto;



import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;


@Getter
@Setter
public class TeaDto {

    private Long price;

    private String category;

    private Long review;

    private Long sale;

    private Long rating;

    private Long rate;

    private String season;

    private String name;

    private String nameEng;

    private boolean caffeine;

    private ArrayList<Long> ingredient;

    private String description;

    private String img;

    private Long inventory;

    private String saleStatus;

    private ArrayList<Long> flavor;

}
