package store.teabliss.review.entity;

import lombok.*;

import java.time.LocalDateTime;



@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long id;

    private String title;

    private String contents;

    private int likes;

    private String img;

    private Long memId;

    private Long teaId;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private int page;

    private int limit;
}
