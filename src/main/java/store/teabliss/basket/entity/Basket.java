package store.teabliss.basket.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basket {

    private Long id;

    private String img;

    private Long productId;

    private String name;

    private String nameEng;

    private Long price;

    private String quality;

    private String type;



}
