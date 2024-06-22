package store.teabliss.payment.entity;


import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayCustomer {



    private String id;

    private String name;

    private String email;

    private HashMap<String,String> address;

    private String zipcode;








}
