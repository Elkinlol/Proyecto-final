package co.avanzada.services.impl;

import co.avanzada.dtos.CreateReviewDTO;
import co.avanzada.services.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Override
    public String createReview(CreateReviewDTO reviewDTO) {
        return "";
    }

    @Override
    public String getReview(String id) {
        return "";
    }
}
