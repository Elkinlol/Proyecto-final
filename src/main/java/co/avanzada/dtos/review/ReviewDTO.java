package co.avanzada.dtos.review;

import co.avanzada.dtos.user.UserDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ReviewDTO(@NotNull @Min(1) @Max(5) int rating,
                        @NotBlank @Length(max = 500) String comment, @NotBlank String nameUser, ReplyReviewDTO replyReview) {
}
