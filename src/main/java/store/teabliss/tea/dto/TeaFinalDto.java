package store.teabliss.tea.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


@Getter
@Setter
public class TeaFinalDto {

    private Long price;

    private String category;

    private Long review;


    private Long sale;

    private Long rating;

    private Long rate;

    private Timestamp createat =new Timestamp(new Date().getTime());

    private boolean IsLastPage = false;

    private String season;

    private String name;

    private String nameEng;

    private String img;


}
