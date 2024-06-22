package store.teabliss.payment.entity;


import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayMethod {

    public EasyPayMethod easyPayMethod;

    @Getter
    @Setter
    public class EasyPayMethod{

        public Card card;

        @Getter
        @Setter
        public class Card{

            private String name;

        }
    }



}


