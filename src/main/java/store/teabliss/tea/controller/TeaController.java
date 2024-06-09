package store.teabliss.tea.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.teabliss.tea.dto.TeaDto;
import store.teabliss.tea.entity.Tea;
import store.teabliss.tea.mapper.TeaMapper;
import store.teabliss.tea.service.TeaService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "차 완제품 API")
@RequestMapping("/api/tea")
public class TeaController {

    private final TeaService teaService;


    @Operation(summary = "티 정보 입력하기", description = "아직 티 하나만 입력가능합니다(향후 수정)")
    @PostMapping("/summit")
    public ResponseEntity<?> summit(@RequestBody TeaDto teaDto){

        teaService.createtea(teaDto);
        return ResponseEntity.ok("완성차 티 정보가 입력되었습니다!");
    }

    @Operation(summary = "티 추천순", description = "페이지네이션 완료 예)http://localhost:8080/api/tea/lowcost?page=4&limit=5")
    @GetMapping("/recommend")
    public ResponseEntity<?> recommend(@RequestParam("page") int page,@RequestParam("limit") int limit) {

        List<Tea> sort=teaService.sort(page,limit);


        return ResponseEntity.ok(sort);
    }

    @Operation(summary = "판매순", description = "페이지네이션 완료")
    @GetMapping("/sale")
    public ResponseEntity<?> sale(@RequestParam("page") int page,@RequestParam("limit") int limit) {

        List<Tea> sort=teaService.salesort(page,limit);

        return ResponseEntity.ok(sort);


    }

    @Operation(summary = "높은가격순", description = "페이지네이션 완료")
    @GetMapping("/topcost")

    public ResponseEntity<?> topcost(@RequestParam("page") int page,@RequestParam("limit") int limit){


        List<Tea> topcost=teaService.topcostsort(page,limit);

        return ResponseEntity.ok(topcost);
    }
//
    @Operation(summary = "낮은가격순", description = "페이지네이션 완료")
    @GetMapping("/lowcost")
    public ResponseEntity<?> lowcost(@RequestParam("page") int page,@RequestParam("limit") int limit ){

        List<Tea> lowcost=teaService.lowcostsort(page,limit);
        return ResponseEntity.ok(lowcost);

    }




}
