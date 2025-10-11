package co.avanzada.services.impl;

import co.avanzada.dtos.review.CreateReplyReviewDTO;
import co.avanzada.dtos.review.CreateReviewDTO;
import co.avanzada.dtos.review.ReplyReviewDTO;
import co.avanzada.dtos.review.ReviewDTO;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.mappers.ReviewMapper;
import co.avanzada.mappers.UserMapper;
import co.avanzada.model.Listing;
import co.avanzada.model.Review;
import co.avanzada.model.User;
import co.avanzada.repository.ListingRepository;
import co.avanzada.repository.ReservationRepository;
import co.avanzada.repository.ReviewRepository;
import co.avanzada.repository.UserRepository;
import co.avanzada.security.AuthUtils;
import co.avanzada.services.ReviewService;
import co.avanzada.services.UserService;
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

    private final ReviewService reviewService;
    private final UserService userService;
    private final AuthUtils authUtils;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final ReviewMapper reviewMapper;
    private final ReservationRepository reservationRepository;
    private final int pageSize= 10;



    @Override
    public ReviewDTO createReview(String listingId, CreateReviewDTO reviewDTO) {

        String currentUserId = authUtils.getCurrentUserId();
        User user = getUserById(currentUserId);
        Listing listing = findListingById(listingId);

        boolean hasCompletedReservation = reservationRepository.hasCompletedReservation(
                user.getId(), listingId);
        if (!hasCompletedReservation) {
            throw new RuntimeException("Solo puede comentar si tuvo una reserva completada");
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
        Pageable pageable = PageRequest.of(page, pageSize);
        return null;
    }

    @Override
    public ReplyReviewDTO replyReview(String listingId, CreateReplyReviewDTO reviewDTO) {
            return null;
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
