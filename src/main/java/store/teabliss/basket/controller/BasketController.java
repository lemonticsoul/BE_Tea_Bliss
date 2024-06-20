package store.teabliss.basket.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.teabliss.basket.dto.BasketDto;
import store.teabliss.basket.dto.DeleteBasketDto;
import store.teabliss.basket.entity.Basket;
import store.teabliss.basket.service.BasketService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "장바구니 API")
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketService basketService;

    @GetMapping("")
    @Operation(summary = "장바구니 검색", description = "")
    public ResponseEntity<?> basket(@RequestParam String email) {

        List<Basket> list = basketService.getbasket(email);

        if (list.size()>0){
            return ResponseEntity.ok(list);
        }
        else{
            return ResponseEntity.status(400).body("조회 실패");
        }
    }

    @PostMapping("")
    @Operation(summary = "장바구니 저장",description = "")
    public ResponseEntity<?> postbasket(@RequestBody BasketDto basketDto){


        boolean baskets=basketService.postBaskets(basketDto);

        if (baskets){
            return ResponseEntity.ok("장바구니 등록 완료!");
        }
        else{
            return ResponseEntity.status(400).body("등록에 실패했네요!");
        }

    }


    @PatchMapping("")
    @Operation(summary = "장바구니 수정",description = "")
    public ResponseEntity<?> patchbasket(@RequestParam("id") Long id,@RequestBody BasketDto basketDto){

        boolean baskets=basketService.patchBaskets(id,basketDto);

        if (baskets){
            return ResponseEntity.ok("장바구니 수정 완료!");
        }else{
            return ResponseEntity.status(400).body("수정 실패!");
        }
    }

    @DeleteMapping("")
    @Operation(summary = "장바구니 하나만 삭제",description = "")
    public ResponseEntity<?> deletebasket(@RequestBody DeleteBasketDto deleteBasketDto){

        boolean baskets=basketService.deleteBaskets(deleteBasketDto);

        if (baskets){
            return ResponseEntity.ok("장바구니 삭제 완료!");
        }else{
            return ResponseEntity.status(400).body("삭제 실패!");
        }
    }


    @DeleteMapping("/selecteddelete")
    @Operation(summary = "장바구니  선택 삭제",description = "")
    public ResponseEntity<?> deletebasket(@RequestBody ArrayList<DeleteBasketDto> deleteBasketDto){


        for (DeleteBasketDto dto :deleteBasketDto) {
            boolean baskets = basketService.deleteBaskets(dto);
        }

        return ResponseEntity.ok("삭제 완료되었습니다.");
    }






}
