package store.teabliss.review.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import store.teabliss.member.entity.Member;
import store.teabliss.review.entity.Review;
import store.teabliss.tea.entity.Tea;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewDto {

    private Long id;

    private String title;

    private String contents;

    private Long like;

    private Member member;

    private Tea tea;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDt;

    public static ReviewDto of(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .contents(review.getContents())
                .like(review.getLikes())
                .createDt(review.getCreateDt())
                .updateDt((review.getUpdateDt()))
                .build();
    }

}
