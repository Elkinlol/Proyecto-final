package co.avanzada.controllers;

import co.avanzada.dtos.review.CreateReplyReviewDTO;
import co.avanzada.dtos.review.CreateReviewDTO;
import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.review.ReviewDTO;
import co.avanzada.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("{/listingId}")
    public ResponseEntity<ResponseDTO<String>> createReview(@PathVariable String listingId ,@Valid @RequestBody CreateReviewDTO reviewDTO){
        reviewService.createReview(listingId , reviewDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<ResponseDTO<Page<ReviewDTO>>> getReview(@RequestParam String id, @RequestParam  int page){
        reviewService.getReview(id, page);
        return ResponseEntity.ok().build();
    }
    // añadir el reply de host

    @PostMapping("/host/{listingId}")
    public ResponseEntity <ResponseDTO<String>> replyReview(@PathVariable String listingId , @Valid @RequestBody CreateReplyReviewDTO reviewDTO ){
        reviewService.replyReview(listingId , reviewDTO);
        return ResponseEntity.ok().build();
    }

}
