package store.teabliss.review.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {


    private Long id;

    private String title;

    private Long like;

    private String contents;

    private Timestamp createat =new Timestamp(new Date().getTime());

    private boolean IsLastPage = false;



}
