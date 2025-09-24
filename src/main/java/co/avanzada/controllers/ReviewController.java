package co.avanzada.controllers;

import co.avanzada.dtos.review.CreateReviewDTO;
import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> createReview(@Valid @RequestBody CreateReviewDTO reviewDTO){
        reviewService.createReview(reviewDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<ResponseDTO<String>> getReview(@RequestParam String id){
        reviewService.getReview(id);
        return ResponseEntity.ok().build();
    }

}
