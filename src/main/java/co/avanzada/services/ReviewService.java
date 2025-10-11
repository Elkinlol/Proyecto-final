package co.avanzada.services;

import co.avanzada.dtos.review.CreateReplyReviewDTO;
import co.avanzada.dtos.review.CreateReviewDTO;
import co.avanzada.dtos.review.ReplyReviewDTO;
import co.avanzada.dtos.review.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ReviewService {
    ReviewDTO createReview( String listingId ,CreateReviewDTO reviewDTO);
    Page<ReviewDTO> getReview(String id, int page );
    ReplyReviewDTO replyReview(String listingId , CreateReplyReviewDTO reviewDTO);
}
