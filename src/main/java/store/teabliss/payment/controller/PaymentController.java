package store.teabliss.payment.controller;




import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import store.teabliss.member.entity.MemberDetails;
import store.teabliss.payment.dto.PaymentRequestDto;
import store.teabliss.payment.dto.PaymentResponseDto;
import store.teabliss.payment.service.PaymentService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "결제 API")
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/complete")
    @Operation(summary = "결제 api 사용 ", description = "결제 api 사용하는 모습입니다.")
    public ResponseEntity<?> payment(@RequestParam ("paymentId") String paymentId,@AuthenticationPrincipal MemberDetails memberDetails) {
        //포트원 요청

        PaymentResponseDto portone=paymentService.portone(paymentId);

        if (portone==null){
            return ResponseEntity.status(400).body("결제된 정보가 없습니다!");
        }
        // 포트원 요청 받고, result 생산

        boolean result=paymentService.payment(portone,memberDetails.getMemberId());



        if (!result){
            return ResponseEntity.status(403).body("데이터베이스에 잘 저장되지 못했습니다.");
        }

        return ResponseEntity.ok(portone);


    }

    @GetMapping("/")
    public ResponseEntity<?> getpayment(@AuthenticationPrincipal MemberDetails memberDetails){

        List<PaymentRequestDto> result=paymentService.getpayment(memberDetails.getMemberId());


        return ResponseEntity.ok(result);



    }
}
