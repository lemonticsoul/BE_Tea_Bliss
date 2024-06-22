package store.teabliss.payment.dto;


import lombok.Getter;
import lombok.Setter;
import store.teabliss.payment.entity.PayAmount;
import store.teabliss.payment.entity.PayCustomer;
import store.teabliss.payment.entity.PayMethod;
import store.teabliss.payment.entity.PayProduct;

import java.util.List;

@Getter
@Setter

public class PaymentResponseDto {

    private String id;

    private String orderName;

    private PayAmount amount;

    private PayMethod method;

    private PayCustomer customer;



    private List<PayProduct> products;

    private String paidAt;


}
