package co.avanzada.controllers;

import co.avanzada.dtos.extras.ResponseUserDTO;
import co.avanzada.dtos.review.CreateReplyReviewDTO;
import co.avanzada.dtos.review.CreateReviewDTO;
import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.review.ReplyReviewDTO;
import co.avanzada.dtos.review.ReviewDTO;
import co.avanzada.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{listingId}")
    public ResponseEntity<ResponseUserDTO<ReviewDTO>> createReview(@PathVariable String listingId ,@Valid @RequestBody CreateReviewDTO reviewDTO){
        ReviewDTO review = reviewService.createReview(listingId , reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUserDTO<>(true, "Review creada correctamente", review));
    }
    @GetMapping("/{listingId}")
    public ResponseEntity<ResponseUserDTO<Page<ReviewDTO>>> getReview(@PathVariable String listingId, @RequestParam  int page){
        Page<ReviewDTO> reviews = reviewService.getReview(listingId, page);
        return ResponseEntity.ok().body(new ResponseUserDTO<>(true, "Se encuentran las siguientes reviews", reviews));
    }
    // añadir el reply de host

    @PostMapping("/host/{reviewId}")
    public ResponseEntity <ResponseUserDTO<ReplyReviewDTO>> replyReview(@PathVariable long reviewId , @Valid @RequestBody CreateReplyReviewDTO reviewDTO ){
        ReplyReviewDTO reply = reviewService.replyReview(reviewId , reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUserDTO<>(true, "Se ha contestado correctamente", reply));
    }

}
