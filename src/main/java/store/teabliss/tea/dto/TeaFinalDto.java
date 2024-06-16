package store.teabliss.tea.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.datetime.DateFormatter;
import store.teabliss.tea.entity.Tea;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Builder
@Getter
@Setter
public class TeaFinalDto {

    private Long price;

    private String category;

    private Long review;

    private Long sale;

    private Long rating;

    private Long rate;

    private boolean isLastPage = false;

    private String season;

    private String name;

    private String nameEng;

    private String img;

    // private LocalDateTime createDt;
    //
    // private LocalDateTime updateDt;
    
    public static List<TeaFinalDto> of(List<Tea> list){

        List<TeaFinalDto> teaFinalDto = new ArrayList<>();
        // var fommatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);

        for (Tea tea : list){
            TeaFinalDto dto = TeaFinalDto.builder()
                    .price(tea.getPrice())
                    .category(tea.getCategory())
                    .review(tea.getReview())
                    .sale(tea.getSale())
                    .rating(tea.getRating())
                    .rate(tea.getRate())
                    .season(tea.getSeason())
                    .name(tea.getName())
                    .nameEng(tea.getNameEng())
                    .img(tea.getImg())
                    .isLastPage(tea.isIsLastPage())
                    // .createDt(LocalDateTime.parse(tea.getCreateDt().toString(), fommatter))
                    // .updateDt(LocalDateTime.parse(tea.getUpdateDt().toString(), fommatter))
                    .build();


            teaFinalDto.add(dto);
        }

        return teaFinalDto;
    }
}
