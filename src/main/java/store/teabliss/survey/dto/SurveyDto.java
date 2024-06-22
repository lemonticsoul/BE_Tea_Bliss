package store.teabliss.survey.dto;

import lombok.Builder;
import lombok.Getter;
import store.teabliss.survey.entity.Survey;
import store.teabliss.tea.dto.TeaFinalDto;
import store.teabliss.tea.entity.Tea;

import java.util.List;

@Getter
@Builder
public class SurveyDto {

    private int taste;

    private int sale;

    private String category;

    private String caffeine;

    private List<TeaFinalDto> teas;

    public static SurveyDto of(Survey survey, List<Tea> list) {
        return SurveyDto.builder()
                .taste(survey.getTaste())
                .sale(survey.getSale())
                .category(survey.getCategory())
                .caffeine(survey.getCaffeine())
                .teas(TeaFinalDto.of(list))
                .build();
    }
}
