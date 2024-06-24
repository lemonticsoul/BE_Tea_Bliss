package store.teabliss.survey.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.survey.entity.Survey;

import java.util.List;

@Mapper
public interface SurveyMapper {

    Long createSurvey(Survey survey);

    List<Survey> findBySurveys();
    Survey findById(Long id);

}
