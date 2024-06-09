package store.teabliss.ingredient.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("")
    @Operation(summary = "차 재료 검색", description = "차 재료 검색 API")
    public ResponseEntity<IngredientResponse> ingredient() {

        List<IngredientResponseDto> list = ingredientService.findByIngredient();

        return ResponseEntity.ok(IngredientResponse.ok(list));
    }



}
