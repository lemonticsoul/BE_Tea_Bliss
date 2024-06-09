package store.teabliss.tea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tea {

    private Long id;

    private String title;

    private Long cost;

    private String category;

    private Long review;

    private Long sale;

    private Long rating;

    private Long rate;

    private String season;

    private Timestamp createat =new Timestamp(new Date().getTime());

    private boolean IsLastPage = false;





}
