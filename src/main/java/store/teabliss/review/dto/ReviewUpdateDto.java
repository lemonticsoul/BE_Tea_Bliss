package store.teabliss.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import store.teabliss.review.entity.Review;

@Getter
public class ReviewUpdateDto {

    @Schema(description = "리뷰 제목", example = "리뷰 제목1")
    private String title;

    @Schema(description = "리뷰 내용", example = "리뷰 내용1")
    private String contents;

    @Schema(description = "좋아요 수", example = "7")
    private Long likes;

    @Schema(description = "완제품 차 고유번호", example = "1")
    private Long teaId;

    public Review toEntity(Long memId) {
        return Review.builder()
                .title(title)
                .contents(contents)
                .likes(likes)
                .teaId(teaId)
                .memId(memId)
                .build();
    }

}
