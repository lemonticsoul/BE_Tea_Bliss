package store.teabliss.review.mapper;


import org.apache.ibatis.annotations.Mapper;
import store.teabliss.review.entity.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {

    long createReview(Review review);

    List<Review> findByAllReview(Review review);

    int countAllReview(Review review);

    List<Review> findByMyReview(Review review);

    int countMyReview(Review review);

    List<Review> topsort(int limit);

    void updateReview(Review review);

}
