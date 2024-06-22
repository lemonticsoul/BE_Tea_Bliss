package store.teabliss.payment.entity;


import lombok.Builder;

@Builder
public class DBProduct {

    private Long payId;

    private String name;

    private Long amount;

    private Long quantity;
}
