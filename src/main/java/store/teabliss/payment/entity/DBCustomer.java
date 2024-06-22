package store.teabliss.payment.entity;

import lombok.Builder;

import java.util.HashMap;


@Builder
public class DBCustomer {

    private Long payId;

    private String id;

    private String name;

    private String email;

    private String oneline;

    private String type;

    private String zipcode;

}
