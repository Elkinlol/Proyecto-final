package co.avanzada.dtos.review;

import co.avanzada.dtos.user.UserDTO;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ReplyReviewDTO(@NotBlank @Length(max= 500) String message, String nameHost) {
}
