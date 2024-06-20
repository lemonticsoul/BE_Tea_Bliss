package store.teabliss.ingredient.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.teabliss.ingredient.dto.IngredientRequestDto;
import store.teabliss.ingredient.dto.IngredientResponse;
import store.teabliss.ingredient.dto.IngredientResponseDto;
import store.teabliss.ingredient.service.IngredientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "차 재료 API")
@RequestMapping("/api/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("")
    @Operation(summary = "차 재료 등록", description = "차 재료 등록 API")
    public ResponseEntity<IngredientResponse> createIngredient(
            @RequestBody IngredientRequestDto ingredientRequestDto
            ) {
        Long id = ingredientService.createIngredient(ingredientRequestDto);

        return ResponseEntity.ok(IngredientResponse.ok(id));
    }

    @GetMapping("")
    @Operation(summary = "차 재료 검색", description = "차 재료 검색 API")
    public ResponseEntity<IngredientResponse> searchIngredients(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "category", required = false) String category
    ) {

        List<IngredientResponseDto> list = ingredientService.findByIngredients(page, limit, category);

        return ResponseEntity.ok(IngredientResponse.ok(list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "차 재료 단일 검색", description = "차 재료 단일 검색 API")
    public ResponseEntity<IngredientResponse> searchIngredient(
            @RequestParam Long id
    ) {

        IngredientResponseDto dto = ingredientService.findByIngredient(id);

        return ResponseEntity.ok(IngredientResponse.ok(dto));
    }

    @PutMapping("/one")
    @Operation(summary = "차 재료 단일 업데이트", description = "차 재료 단일 업데이트 API")
    public ResponseEntity<IngredientResponse> updateIngredient(
            @RequestBody IngredientRequestDto ingredientRequestDto
    ) {
        int success = ingredientService.updateIngredient(ingredientRequestDto);

        return ResponseEntity.ok(IngredientResponse.ok(success));
    }

    @PutMapping("/many")
    @Operation(summary = "차 재료 다중 업데이트", description = "차 재료 다중 업데이트 API")
    public ResponseEntity<IngredientResponse> updateIngredients(
            @RequestBody List<IngredientRequestDto> ingredientRequestDtos
    ) {

        int success = ingredientService.updateIngredients(ingredientRequestDtos);

        return ResponseEntity.ok(IngredientResponse.ok(success));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "차 재료 삭제", description = "차 재료 삭제 API")
    public ResponseEntity<IngredientResponse> deleteIngredient(
            @PathVariable Long id
    ) {

        ingredientService.deleteIngredient(id);

        return ResponseEntity.ok(IngredientResponse.ok("재료 삭제가 성공 되었습니다."));
    }



}
