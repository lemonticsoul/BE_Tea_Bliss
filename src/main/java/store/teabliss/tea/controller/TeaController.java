package store.teabliss.tea.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import store.teabliss.tea.dto.TeaDto;
import store.teabliss.tea.dto.TeaFinalDto;
import store.teabliss.tea.dto.TeaPatchDto;
import store.teabliss.tea.dto.TeaSearchDto;
import store.teabliss.tea.entity.Tea;
import store.teabliss.tea.mapper.TeaMapper;
import store.teabliss.tea.service.TeaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "차 완제품 API")
@RequestMapping("/api/tea")
public class TeaController {

    private final TeaService teaService;


    @Operation(summary = "티 정보 입력하기", description = "아직 티 하나만 입력가능합니다(향후 수정)")
    @PostMapping("/summit")
    public ResponseEntity<?> summit(@RequestBody TeaDto teaDto) throws JsonProcessingException {

        teaService.createtea(teaDto);
        return ResponseEntity.ok("완성차 티 정보가 입력되었습니다!");
    }

    @Operation(summary = "티 추천순", description = "페이지네이션 완료 예)http://localhost:8080/api/tea/lowcost?page=4&limit=5")
    @GetMapping("/recommend")
    public ResponseEntity<?> recommend(@RequestParam("page") int page,@RequestParam("limit") int limit) {

        List<TeaFinalDto> sort=teaService.sort(page,limit);
        Long teacount=teaService.count();

        Map<String,Object> response=new HashMap<>();
        response.put("size",teacount);
        response.put("tea",sort);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "판매순", description = "페이지네이션 완료")
    @GetMapping("/sale")
    public ResponseEntity<?> sale(@RequestParam("page") int page,@RequestParam("limit") int limit) {

        List<TeaFinalDto> sort=teaService.salesort(page,limit);

        Long teacount=teaService.count();

        Map<String,Object> response=new HashMap<>();
        response.put("size",teacount);
        response.put("tea",sort);
        return ResponseEntity.ok(response);


    }

    @Operation(summary = "높은가격순", description = "페이지네이션 완료")
    @GetMapping("/topcost")

    public ResponseEntity<?> topcost(@RequestParam("page") int page,@RequestParam("limit") int limit){


        List<TeaFinalDto> topcost=teaService.topcostsort(page,limit);

        Long teacount=teaService.count();

        Map<String,Object> response=new HashMap<>();
        response.put("size",teacount);
        response.put("tea",topcost);
        return ResponseEntity.ok(response);
    }
//
    @Operation(summary = "낮은가격순", description = "페이지네이션 완료")
    @GetMapping("/lowcost")
    public ResponseEntity<?> lowcost(@RequestParam("page") int page,@RequestParam("limit") int limit ){

        List<TeaFinalDto> lowcost=teaService.lowcostsort(page,limit);
        Long teacount=teaService.count();


        Map<String,Object> response=new HashMap<>();
        response.put("size",teacount);
        response.put("tea",lowcost);
        return ResponseEntity.ok(response);

    }

    @GetMapping("all")
    @Operation(summary = "모두 조회", description = "모든 차를 조회하는 로직입니다.")
    public ResponseEntity<?> all(@RequestParam("page") int page,@RequestParam("limit") int limit ){

        List<TeaFinalDto> all=teaService.all(page,limit);

        Long teacount=teaService.count();

        Map<String,Object> response=new HashMap<>();
        response.put("size",teacount);
        response.put("tea",all);
        return ResponseEntity.ok(response);


    }
    @GetMapping("findtea")
    @Operation(summary = "티 상세 조회", description = "차 하나만 조회하는 로직")
    public ResponseEntity<TeaSearchDto> responseid(@RequestParam("id") int id){


        TeaSearchDto tea=teaService.find(id);

        return ResponseEntity.ok(tea);


    }

    @GetMapping("category")
    @Operation(summary = "카테고리 조회", description = "차 하나만 조회하는 로직")
    public ResponseEntity<?> category(@RequestParam("page") int page,@RequestParam("limit") int limit,@RequestParam("category") String categroy){


        List<TeaFinalDto> category=teaService.categorysort(page,limit,categroy);

        Long teacount=teaService.categorycount(categroy);

        Map<String,Object> response=new HashMap<>();
        response.put("size",teacount);
        response.put("tea",category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("season")
    @Operation(summary = "시즌별 조회", description = "차 하나만 조회하는 로직")
    public ResponseEntity<?> season(@RequestParam("page") int page,@RequestParam("limit") int limit,@RequestParam("season") String season){

        List<TeaFinalDto> seasonsort=teaService.sortseason(page,limit,season);

        int teacount=seasonsort.size();


        Map<String,Object> response=new HashMap<>();
        response.put("size",teacount);
        response.put("tea",seasonsort);
        return ResponseEntity.ok(response);

    }

    @GetMapping("caffeine")
    @Operation(summary = "카페인 조회", description = "차 하나만 조회하는 로직")
    public ResponseEntity<?> caffeine(@RequestParam("page") int page,@RequestParam("limit") int limit,@RequestParam("caffeine") boolean caffeine){

        List<TeaFinalDto> caffeinesort=teaService.caffeinesort(page,limit,caffeine);

        int teacount=caffeinesort.size();
        Map<String,Object> response=new HashMap<>();
        response.put("size",teacount);
        response.put("tea",caffeinesort);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "완성차 삭제 ", description = "차 하나만 조회하는 로직")
    public ResponseEntity<?> teaDelete(@PathVariable int id){

        boolean delete=teaService.deletetea(id);
        if (delete) {
            return ResponseEntity.ok("삭제되었습니다.");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 실패!");
        }
    }

    @PatchMapping("patch/{id}")
    @Operation(summary = "완성차 수정 ", description = "차 하나만 조회하는 로직")
    public ResponseEntity<?> teaPatch(TeaPatchDto teaPatchDto){



        boolean patch=teaService.patchtea(teaPatchDto);

        if (patch) {
            return ResponseEntity.ok("수정되었습니다.");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 실패!");
        }

    }







}
