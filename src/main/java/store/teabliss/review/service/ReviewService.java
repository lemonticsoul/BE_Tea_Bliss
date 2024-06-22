package store.teabliss.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import store.teabliss.member.entity.MemberDetails;
import store.teabliss.review.dto.ReviewCreateDto;
import store.teabliss.review.dto.ReviewDto;
import store.teabliss.review.dto.ReviewResponse;
import store.teabliss.review.dto.ReviewUpdateDto;
import store.teabliss.review.entity.Review;
import store.teabliss.review.mapper.ReviewMapper;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    public long createReview(Long memId, ReviewCreateDto reviewCreateDto) {
        Review review = reviewCreateDto.toEntity(memId);

        return reviewMapper.createReview(review);
    }

    public ReviewResponse topReview(int limit){
        List<Review> topReview = reviewMapper.topSort(limit);

        List<ReviewDto> reviewDtos = new ArrayList<>();

        for(Review review : topReview) {
            ReviewDto dto = ReviewDto.of(review);
            reviewDtos.add(dto);
        }

        return ReviewResponse.ok(reviewDtos, null, limit);
    }


    public ReviewResponse reviews(int page, int limit) {

        List<ReviewDto> reviewDtos = new ArrayList<>();

        int pagination = limit * (page - 1);

        Review search = Review.builder()
                .memId(null)
                .teaId(null)
                .page(pagination)
                .limit(limit)
                .build();

        List<Review> reviews = reviewMapper.findByAllReview(search);
        int count = reviewMapper.countAllReview(search);

        int limitPage = (int) Math.ceil((double) count / limit);

        for(Review review : reviews) {
            ReviewDto dto = ReviewDto.of(review);
            reviewDtos.add(dto);
        }

        // double all = reviews.size();
        // int limitPage= (int) Math.ceil(all/limit);

        // for (int i = limit * (page - 1); i < (page * limit); i++){
        //              if (i==all) {
        //                 break;
        //             }
        //             if (page==limitpage){
        //                 reviews.get(i).setIsLastPage(true);
        //                 new_list.add(reviews.get(i));
        //
        //             } else if (page !=limitpage) {
        //                 reviews.get(i).setIsLastPage(false);
        //                 new_list.add(reviews.get(i));
        //
        //             }
        //
        // }

        return ReviewResponse.ok(reviewDtos, page == limitPage, count);
    }

    public ReviewResponse mypage(Long memId, int page, int limit, boolean status) {

        List<ReviewDto> reviewDtos = new ArrayList<>();

        int pagination = limit * (page - 1);

        Review search = Review.builder()
                .memId(memId)
                .page(pagination)
                .limit(limit)
                .build();

        List<Review> reviews = reviewMapper.findByMyReview(search);
        int count = reviewMapper.countMyReview(search);

        int limitPage = (int) Math.ceil((double) count / limit);

        for(Review review : reviews) {
            ReviewDto dto = ReviewDto.of(review);
            reviewDtos.add(dto);
        }

        return ReviewResponse.ok(reviewDtos, page == limitPage, count);
    }

    public void updateReview(Long memId, ReviewUpdateDto reviewUpdateDto) {
        Review review = reviewUpdateDto.toEntity(memId);

        reviewMapper.updateReview(review);
    }


}
