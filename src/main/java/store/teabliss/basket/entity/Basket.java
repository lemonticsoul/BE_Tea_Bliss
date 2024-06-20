package store.teabliss.basket.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Basket {

    private Long id;

    private String email;

    private String img;

    private String product;

    private String name;

    private String nameEng;

    private Long price;

    private Long quantity;

    private String type;



}
