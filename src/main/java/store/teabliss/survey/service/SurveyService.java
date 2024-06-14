package store.teabliss.survey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.survey.dto.SurveyCreateDto;
import store.teabliss.survey.entity.Survey;
import store.teabliss.survey.mapper.SurveyMapper;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyMapper surveyMapper;

    public Long createSurvey(Long memId, SurveyCreateDto surveyCreateDto) {

        Survey survey = surveyCreateDto.toEntity(memId);

        return surveyMapper.createSurvey(survey);
    }

}
