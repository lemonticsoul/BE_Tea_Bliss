package store.teabliss.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import store.teabliss.payment.entity.DBProduct;
import store.teabliss.review.entity.Review;

import java.util.List;


@Builder
@Getter
@ToString
public class PaymentRequestDto {

    private Long paymentid;


    private List<Object> productList;


    private String paidAt;



    public static PaymentRequestDto of(Long paymentid,List<Object> productList,String paidAt) {
        return PaymentRequestDto.builder().paymentid(paymentid)
                                .productList(productList)
                .paidAt(paidAt)
                .build();
    }
}
