package store.teabliss.survey.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.survey.entity.Survey;

@Mapper
public interface SurveyMapper {

    Long createSurvey(Survey survey);

    Survey findById(Long id);

}
