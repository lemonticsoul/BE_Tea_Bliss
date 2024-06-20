package store.teabliss.review.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import store.teabliss.member.entity.MemberDetails;
import store.teabliss.review.dto.ReviewCreateDto;
import store.teabliss.review.dto.ReviewResponse;
import store.teabliss.review.dto.ReviewUpdateDto;
import store.teabliss.review.entity.Review;
import store.teabliss.review.service.ReviewService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰 API")
@RequestMapping("/api/review")
public class ReviewController {


    private final ReviewService reviewservice;

    @Operation(summary = "리뷰 등록", description = "리뷰 등록 API")
    @PostMapping("")
    public ResponseEntity<ReviewResponse> createReview(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody ReviewCreateDto reviewCreateDto
    ) {
        long id = reviewservice.createReview(memberDetails.getMemberId(), reviewCreateDto);

        return ResponseEntity.ok(ReviewResponse.ok(id));
    }

    @Operation(summary = "전체 top 리뷰 ", description = "퀴리 파라미터는 limit 한개 만 보내주세요!")
    @GetMapping("/review-list/order")
    public ResponseEntity<?> summit(@RequestParam int limit){

        List<Review> reviews = reviewservice.topreview(limit);

        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "모든 리뷰 조회", description = "페이지네이션 가능!")
    @GetMapping("/review-list")
    public ResponseEntity<ReviewResponse> summit(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam int page,
            @RequestParam int limit
    ){
        ReviewResponse reviewResponse = reviewservice.reviews(page, limit);

        return ResponseEntity.ok(reviewResponse);
    }

    @Operation(summary = "마이페이지 리뷰 리스트", description = "마이페이지에서 확인하는 본인의 리뷰 리스트")
    @GetMapping("/my-reviews")
    public ResponseEntity<ReviewResponse> mypage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam(required = false) String status
    ) {

        ReviewResponse reviewResponse = reviewservice.mypage(memberDetails.getMemberId(), page, limit, Boolean.parseBoolean(status));

        return ResponseEntity.ok(reviewResponse);
    }

    @Operation(summary = "리뷰 수정", description = "리뷰 수정 API")
    @PupMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody ReviewUpdateDto reviewUpdateDto,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ReviewResponse.ok(null));
    }





}
