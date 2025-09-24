package co.avanzada.services;

import co.avanzada.dtos.review.CreateReviewDTO;


public interface ReviewService {
    String createReview( CreateReviewDTO reviewDTO);
    String getReview( String id);
}
