package store.teabliss.payment.entity;


import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
public class DBPayment {

    private Long id;

    private Long memId;

    private String paymentId;

    private String orderName;

    private String paidAt;



}
