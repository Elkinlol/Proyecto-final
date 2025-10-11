package co.avanzada.services.impl;

import co.avanzada.dtos.review.CreateReplyReviewDTO;
import co.avanzada.dtos.review.CreateReviewDTO;
import co.avanzada.dtos.review.ReplyReviewDTO;
import co.avanzada.dtos.review.ReviewDTO;
import co.avanzada.exception.ConflictException;
import co.avanzada.exception.ForbiddenException;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.mappers.ReviewMapper;
import co.avanzada.model.Listing;
import co.avanzada.model.Reply;
import co.avanzada.model.Review;
import co.avanzada.model.User;
import co.avanzada.repository.*;
import co.avanzada.security.AuthUtils;
import co.avanzada.services.ReviewService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@Builder
@Service
public class ReviewServiceImpl implements ReviewService {



    private final AuthUtils authUtils;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final ReviewMapper reviewMapper;
    private final ReservationRepository reservationRepository;
    private final int pageSize= 10;
    private final ReplyRepository replyRepository;



    @Override
    public ReviewDTO createReview(String listingId, CreateReviewDTO reviewDTO) {

        String currentUserId = authUtils.getCurrentUserId();
        User user = getUserById(currentUserId);
        Listing listing = findListingById(listingId);

        boolean hasCompletedReservation = reservationRepository.hasAnyCompletedReservation(
                user.getId());
        if (!hasCompletedReservation) {
            throw new ConflictException("Solo puede comentar si tuvo una reserva completada");
        }

        // Validación máximo 1 comentario por reserva
        boolean alreadyReviewed = reviewRepository.existsByUserAndListing(currentUserId, listingId);
        if (alreadyReviewed) {
            throw new RuntimeException("Ya has comentado este alojamiento");
        }
        Review review = reviewMapper.toReview(reviewDTO);
        review.setUser(user);
        review.setListing(listing);
        listing.setAverageRating(reviewRepository.getAverageRating(listingId));
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toReviewDTO(savedReview);
    }

    @Override
    public Page<ReviewDTO> getReview(String id, int page ) {
        String currentUserId = authUtils.getCurrentUserId();
        getUserById(currentUserId);
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Review> reviews = reviewRepository.findReviewsByListing(id, pageable);
        return reviews.map(reviewMapper::toReviewDTO);
    }

    @Override
    public ReplyReviewDTO replyReview(Long reviewId, CreateReplyReviewDTO reviewDTO) {
        String currentUserId = authUtils.getCurrentUserId();
        User user = getUserById(currentUserId);
        if(!reviewRepository.findById(reviewId).isPresent()) {
            throw new ResourceNotFoundException("Review no encontrada");
        }
        Review review = reviewRepository.findById(reviewId).get();
        if (!review.getListing().getHost().getId().equals(currentUserId)) {
            throw new ForbiddenException("No autorizado para responder este comentario");
        }
        Reply reply = reviewMapper.toReply(reviewDTO);
        reply.setHost(user);
        reply.setReview(review);
        replyRepository.save(reply);
        reviewRepository.save(review);
        return reviewMapper.toReplyReviewDTO(reply);
    }


    private User getUserById(String id) {
        if(!userRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return userRepository.findById(id).get();
    }

    private Listing findListingById(String id){
        if(!listingRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Alojamiento no encontrado");
        }
        return listingRepository.findById(id).get();
    }
}
