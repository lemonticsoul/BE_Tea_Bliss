package store.teabliss.review.mapper;


import org.apache.ibatis.annotations.Mapper;
import store.teabliss.review.entity.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {


    List<Review> all();

    List<Review> topsort(int limit);


}
