package store.teabliss.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.review.entity.Review;
import store.teabliss.review.mapper.ReviewMapper;
import store.teabliss.tea.entity.Tea;
import store.teabliss.tea.mapper.TeaMapper;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class ReviewService {




    private final ReviewMapper reviewMapper;



    public List<Review> topreview(int limit){

        List<Review> topreview=reviewMapper.topsort(limit);


        return topreview;


    }


    public List<Review> reviews(int page,int limit){

        List<Review> reviews=reviewMapper.all();

        double all=reviews.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Review> new_list=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                reviews.get(i).setIsLastPage(true);
                new_list.add(reviews.get(i));

            } else if (page !=limitpage) {
                reviews.get(i).setIsLastPage(false);
                new_list.add(reviews.get(i));

            }

        }

        return new_list;


    }
}
