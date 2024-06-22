package store.teabliss.payment.entity;


import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayProduct {

    private Long id;

    private String name;

    private Long amount;

    private Long quantity;


}
