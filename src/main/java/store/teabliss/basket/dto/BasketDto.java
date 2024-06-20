package store.teabliss.basket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class BasketDto {

    private String email;//유저 아이디=이메일형식이라 그런거임 ㅇㅇ
    

    private String product;

    private Long quantity;

    private String type;

}
