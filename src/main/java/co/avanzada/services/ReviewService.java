package co.avanzada.services;

import co.avanzada.dtos.CreateReviewDTO;


public interface ReviewService {
    String createReview( CreateReviewDTO reviewDTO);
    String getReview( String id);
}
