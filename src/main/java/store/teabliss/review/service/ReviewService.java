package store.teabliss.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.review.dto.ReviewDto;
import store.teabliss.review.dto.ReviewResponse;
import store.teabliss.review.entity.Review;
import store.teabliss.review.mapper.ReviewMapper;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    public List<Review> topreview(int limit){
        List<Review> topreview = reviewMapper.topsort(limit);

        return topreview;
    }


    public ReviewResponse reviews(int page, int limit){

        List<ReviewDto> reviewDtos = new ArrayList<>();

        int pagination = limit * (page - 1);

        Review search = Review.builder()
                .memId(null)
                .teaId(null)
                .page(pagination)
                .limit(limit)
                .build();

        List<Review> reviews = reviewMapper.all(search);
        int count = reviewMapper.countAll(search);

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

        return ReviewResponse.ok(reviews, page == limitPage);
    }
}
