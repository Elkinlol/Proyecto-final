package co.avanzada.mappers;

import co.avanzada.dtos.review.CreateReplyReviewDTO;
import co.avanzada.dtos.review.CreateReviewDTO;
import co.avanzada.dtos.review.ReplyReviewDTO;
import co.avanzada.dtos.review.ReviewDTO;
import co.avanzada.model.Reply;
import co.avanzada.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface ReviewMapper {


    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "rating", source = "rating" )
    Review toReview(CreateReviewDTO createReviewDTO);

    @Mapping(target = "nameUser", source = "user.fullName")
    ReviewDTO toReviewDTO(Review review);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "message", source = "message")
    Reply toReply(CreateReplyReviewDTO replyReviewDTO);

    @Mapping(target = "message", source = "message")
    @Mapping(target = "nameHost", source = "host.fullName")
    ReplyReviewDTO toReplyReviewDTO(Reply reply);

}
