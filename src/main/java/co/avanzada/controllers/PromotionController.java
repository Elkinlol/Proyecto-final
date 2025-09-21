package co.avanzada.controllers;

import co.avanzada.dtos.CreatePromotionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @PostMapping("/Lisings/{id}")
    public ResponseEntity<String> createPromotion(@PathVariable String id, @RequestBody CreatePromotionDTO createPromotionDTO){
        return ResponseEntity.ok().build();
    }
    @GetMapping("/Listings/{id}")
    public ResponseEntity<String> getPromotions(@PathVariable String id){
        return ResponseEntity.ok().build();
    }


}
