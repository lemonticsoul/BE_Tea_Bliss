package store.teabliss.survey.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Survey {

    private Long id;

    private int taste;

    private String sale;

    private String category;

    private String caffeine;

    private Long memId;

}
