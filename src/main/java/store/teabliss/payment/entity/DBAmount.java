package store.teabliss.payment.entity;


import lombok.Builder;

@Builder
public class DBAmount {

    private Long total;

    private Long paid;

    private Long payId;
}
