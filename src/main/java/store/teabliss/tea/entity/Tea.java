package store.teabliss.tea.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tea {

    private Long id;

    private Long price;

    private String category;

    private Long review;

    private Long sale;

    private Long rating;

    private Long rate;

    private String season;

    private boolean IsLastPage = false;

    private String name;

    private String nameEng;

    private boolean caffeine;

    private String description;

    private String img;

    private Long inventory;

    private String saleStatus;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    /**
     * 가격대 검색을 위한 컬럼
      */
    private int priceStart;

    private int priceEnd;

}
