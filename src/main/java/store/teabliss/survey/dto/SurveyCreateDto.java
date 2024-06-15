package store.teabliss.survey.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import store.teabliss.survey.entity.Survey;

@Getter
public class SurveyCreateDto {

    @Schema(description = "맛", example = "1")
    private int taste;

    @Schema(description = "원하는 가격대", example = "10,000원대")
    private String sale;

    @Schema(description = "원하는 재료", example = "Black,Pu Erh")
    private String category;

    @Schema(description = "카페인 유무", example = "Y")
    private String caffeine;

    @Builder
    public Survey toEntity(Long memId) {
        return Survey.builder()
                .taste(taste)
                .sale(sale)
                .category(category)
                .caffeine(caffeine)
                .memId(memId)
                .build();
    }

}
