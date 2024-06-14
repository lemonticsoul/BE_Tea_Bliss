package store.teabliss.survey.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.teabliss.member.entity.MemberDetails;
import store.teabliss.survey.dto.SurveyCreateDto;
import store.teabliss.survey.dto.SurveyResponse;
import store.teabliss.survey.service.SurveyService;

@RestController
@RequiredArgsConstructor
@Tag(name = "설문조사 API")
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("")
    public ResponseEntity<SurveyResponse> createSurvey(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody SurveyCreateDto surveyCreateDto
            ) {

        Long id = surveyService.createSurvey(memberDetails.getMemberId(), surveyCreateDto);

        return ResponseEntity.ok(SurveyResponse.ok("정상적으로 설문조사가 등록되었습니다.", id));
    }

}
