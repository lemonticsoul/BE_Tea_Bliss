package store.teabliss.survey.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import store.teabliss.member.entity.MemberDetails;
import store.teabliss.survey.dto.SurveyCreateDto;
import store.teabliss.survey.dto.SurveyDto;
import store.teabliss.survey.dto.SurveyResponse;
import store.teabliss.survey.service.SurveyService;

@RestController
@RequiredArgsConstructor
@Tag(name = "설문조사 API")
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("")
    @Operation(summary = "설문조사 등록", description = "설문조사 등록 API")
    public ResponseEntity<SurveyResponse> createSurvey(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody SurveyCreateDto surveyCreateDto
            ) {

        Long id = surveyService.createSurvey(memberDetails.getMemberId(), surveyCreateDto);

        return ResponseEntity.ok(SurveyResponse.ok("정상적으로 설문조사가 등록되었습니다.", id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "설문조사 조회", description = "설문조사 조회 API")
    public ResponseEntity<SurveyResponse> getSurvey(
            @PathVariable Long id
    ) {

        SurveyDto surveyDto = surveyService.findById(id);

        return ResponseEntity.ok(SurveyResponse.ok(surveyDto));
    }

}
