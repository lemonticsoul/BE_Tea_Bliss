package store.teabliss.payment.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import store.teabliss.basket.mapper.BasketMapper;
import store.teabliss.payment.dto.PaymentRequestDto;
import store.teabliss.payment.dto.PaymentResponseDto;
import store.teabliss.payment.entity.*;
import store.teabliss.payment.mapper.PaymentMapper;
import store.teabliss.review.entity.Review;
import store.teabliss.review.mapper.ReviewMapper;
import store.teabliss.tea.mapper.TeaMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    //mapper 설정
    private final TeaMapper teaMapper;
    private final BasketMapper basketMapper;
    private final PaymentMapper paymentMapper;
    private final ReviewMapper reviewMapper;

    //amount 설정
    PayAmount payAmount=new PayAmount();
    PayCustomer payCustomer=new PayCustomer();
    PayMethod payMethod=new PayMethod();
    PayMethod.EasyPayMethod easyPayMethod= payMethod.new EasyPayMethod();
    PayMethod.EasyPayMethod.Card card=easyPayMethod.new Card();

    //webclient 설정
    WebClient client=WebClient.create("https://api.portone.io/payments");

    public PaymentResponseDto portone(String paymentId){

        // api 요청
        Mono<PaymentResponseDto> result=client.get()
                .uri("/{paymentId}",paymentId)
                .header("Authorization", "${PORTONE_BEARTOKEN}")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PaymentResponseDto.class);


        PaymentResponseDto response = result.block();

        // 결과 확인
        return response;

    }

    public boolean payment(PaymentResponseDto paymentResponseDto,Long memId){

        List<PayProduct> productList=paymentResponseDto.getProducts();

        Long paidprice=paymentResponseDto.getAmount().getPaid();
        Long totalprice=paymentResponseDto.getAmount().getTotal();

        if (!paidprice.equals(totalprice)) {
            throw new RuntimeException("금액과 실제 결제 금액과 일치 하지 않습니다!");
        }

        //product 하나만 실행

        DBPayment payment = DBPayment.builder()
                .memId(memId)
                .paymentId(paymentResponseDto.getId())
                .orderName(paymentResponseDto.getOrderName())
                .build();

        paymentMapper.savepayment(payment);

        for (PayProduct product : productList) {

            String name = product.getName();
            Long quantity = product.getQuantity();

            //결제가 되었으면,product 닉넴을 찾아 재고 수량만큼 마이너스 하기
            String saleStatus = teaMapper.searchstatus(name);
            Long inventory = teaMapper.searchinventory(name);



            if (saleStatus=="품절" || inventory<quantity) {
                throw new RuntimeException("상품이 품절되었습니다!");
            }

            //inventory 변경
            teaMapper.updateinventory(name, quantity);
            //판매수량 변경
            teaMapper.updatesale(name, quantity);

            String result=basketMapper.search(name,memId);

            //장바구니에 결제한 상품이 있다면, 상품을 없애는 로직
            if (result==name){
                basketMapper.deleteproduct(name,memId);
            }


            //모든 예외 처리를 거치고 난뒤, db 에 저장
            DBProduct saveproduct = DBProduct.builder()
                    .payId(payment.getId())
                    .name(product.getName())
                    .amount(product.getAmount())
                    .quantity(product.getQuantity())
                    .build();

            paymentMapper.saveproduct(saveproduct);
        }

        HashMap<String,String> Address=paymentResponseDto.getCustomer().getAddress();
        ArrayList<String> result=new ArrayList<>();

        for (String key:Address.values()){
            result.add(key);
        }

        DBCustomer dbCustomer=DBCustomer.builder()
                .payId(payment.getId())
                .id(paymentResponseDto.getCustomer().getId())
                .name(paymentResponseDto.getCustomer().getName())
                .email(paymentResponseDto.getCustomer().getEmail())
                .oneline(result.get(0))
                .type(result.get(1))
                .zipcode(paymentResponseDto.getCustomer().getZipcode())
                .build();

        DBAmount dbAmount=DBAmount.builder()
                .payId(payment.getId())
                .paid(paymentResponseDto.getAmount().getPaid())
                .total(paymentResponseDto.getAmount().getTotal())
                .build();

        DBCard   dbCard= DBCard.builder()
                        .payId(payment.getId())
                        .name(paymentResponseDto.getMethod().getEasyPayMethod().getCard().getName())
                .build();



        paymentMapper.savecustomer(dbCustomer);
        paymentMapper.saveamount(dbAmount);
        paymentMapper.savecard(dbCard);



        //db 저장 되면, 이를 reqdto에 보내는 로직

        return true;






    }


    public List<PaymentRequestDto> getpayment(Long id){

        //일단 payment의 고유 아이디 구해서
        List<DBPayment> payment=paymentMapper.searchpayid(id);

        List<PaymentRequestDto> paymentRequestDtos =new ArrayList<>();

        for (DBPayment pay:payment) {
            //그중에서 모든 구매 prodcut 구하고
            Long paymentId= pay.getId();
            List<DBProduct> products = paymentMapper.searchprodcut(pay.getId());
            List<Object> finalproducts=new ArrayList<>();

            for (DBProduct product : products){
                Map<String,Object> response=new HashMap<>();

                String name= product.getName();
                Long findId=teaMapper.findByidandname(name);

                boolean reviewok=reviewMapper.findByteaidandmember(findId,id);

                response.put("review",reviewok);
                response.put("product",product);

                finalproducts.add(response);

            }

            //paid_at 도 구한다.

            String paid_at = pay.getPaidAt();
            paymentRequestDtos.add(PaymentRequestDto.of(paymentId,finalproducts,paid_at));


        }

        return paymentRequestDtos;




    }
}
