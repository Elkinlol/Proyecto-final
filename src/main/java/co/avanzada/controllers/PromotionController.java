package co.avanzada.controllers;

import co.avanzada.dtos.promotionDTO.CreatePromotionDTO;
import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.services.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotion")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;
    @PostMapping("/Lisings/{id}")
    public ResponseEntity<ResponseDTO<String>> createPromotion(@PathVariable String id, @Valid @RequestBody CreatePromotionDTO createPromotionDTO){
        promotionService.createPromotion(id, createPromotionDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/Listings/{id}")
    public ResponseEntity<ResponseDTO<String>> getPromotions(@PathVariable String id){
        promotionService.getPromotions(id);
        return ResponseEntity.ok().build();
    }


}
