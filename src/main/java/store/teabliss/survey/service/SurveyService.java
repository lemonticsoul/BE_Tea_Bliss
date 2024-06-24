package store.teabliss.survey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.survey.dto.SurveyCreateDto;
import store.teabliss.survey.dto.SurveyDto;
import store.teabliss.survey.entity.Survey;
import store.teabliss.survey.mapper.SurveyMapper;
import store.teabliss.tea.entity.Tea;
import store.teabliss.tea.mapper.TeaMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyMapper surveyMapper;
    private final TeaMapper teaMapper;

    public Long createSurvey(Long memId, SurveyCreateDto surveyCreateDto) {

        Survey survey = surveyCreateDto.toEntity(memId);

        surveyMapper.createSurvey(survey);

        return survey.getSurveyId();
    }

    public List<SurveyDto> findBySurveys() {
        List<SurveyDto> surveyDtos = new ArrayList<>();

        List<Survey> surveys = surveyMapper.findBySurveys();

        for(Survey s : surveys) {
            Tea tea = Tea.builder()
                    .priceStart(s.getSale())
                    .priceEnd(s.getSale() + 10000)
                    .category(s.getCategory())
                    .caffeine(s.getCaffeine().equals("N"))
                    .build();

            List<Tea> teas = teaMapper.surveyRecommendTea(tea);

            SurveyDto surveyDto = SurveyDto.of(s, teas);

            surveyDtos.add(surveyDto);
        }

        return surveyDtos;
    }

    public SurveyDto findById(Long id) {

        Survey survey = surveyMapper.findById(id);

        Tea tea = Tea.builder()
                .priceStart(survey.getSale())
                .priceEnd(survey.getSale() + 10000)
                .category(survey.getCategory())
                .caffeine(survey.getCaffeine().equals("N"))
                .build();

        List<Tea> teas = teaMapper.surveyRecommendTea(tea);

        return SurveyDto.of(survey, teas);
    }

}
