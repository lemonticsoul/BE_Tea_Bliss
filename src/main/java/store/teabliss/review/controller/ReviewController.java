package store.teabliss.review.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.teabliss.review.entity.Review;
import store.teabliss.review.service.ReviewService;
import store.teabliss.tea.dto.TeaDto;
import store.teabliss.tea.service.TeaService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "차 완제품 API")
@RequestMapping("/api/review")
public class ReviewController {


    private final ReviewService reviewservice;


    @Operation(summary = "전체 top 리뷰 ", description = "퀴리 파라미터는 limit 한개 만 보내주세요!")
    @GetMapping("/review-list/order")
    public ResponseEntity<?> summit(@RequestParam int limit){

        List<Review> reviews=reviewservice.topreview(limit);

        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "모든 리뷰 조회", description = "페이지네이션 가능!")
    @GetMapping("/review-list")
    public ResponseEntity<?> summit(@RequestParam int page,@RequestParam int limit){

        List<Review> reviews=reviewservice.reviews(page,limit);

        return ResponseEntity.ok(reviews);
    }








}
