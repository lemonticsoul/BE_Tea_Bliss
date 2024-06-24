package store.teabliss.payment.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class DBProduct {

    private Long payId;

    private String name;

    private Long amount;

    private Long quantity;
}
