package co.avanzada.controllers;

import co.avanzada.dtos.CreateReviewDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Review")
public class ReviewController {

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody CreateReviewDTO reviewDTO){
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<String> getReview(@RequestParam String id){
        return ResponseEntity.ok().build();
    }

}
