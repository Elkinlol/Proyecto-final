package co.avanzada.dtos.review;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateReplyReviewDTO(@NotBlank @Length (max= 500) String message) {
}
