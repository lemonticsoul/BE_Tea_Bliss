package store.teabliss.payment.entity;


import lombok.Builder;

@Builder
public class DBCard {

    private Long payId;

    private String name;
}
