package co.avanzada.services;

import co.avanzada.dtos.review.CreateReviewDTO;
import co.avanzada.dtos.review.ReviewDTO;

import java.util.List;


public interface ReviewService {
    void createReview( CreateReviewDTO reviewDTO);
    List<ReviewDTO> getReview(String id);
}
