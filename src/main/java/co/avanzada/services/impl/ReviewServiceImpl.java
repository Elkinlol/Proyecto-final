package co.avanzada.services.impl;

import co.avanzada.dtos.review.CreateReviewDTO;
import co.avanzada.dtos.review.ReviewDTO;
import co.avanzada.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Override
    public void createReview(CreateReviewDTO reviewDTO) {
        return ;
    }

    @Override
    public List<ReviewDTO> getReview(String id) {
        return null;
    }
}
