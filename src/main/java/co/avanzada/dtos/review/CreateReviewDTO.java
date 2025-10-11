package co.avanzada.dtos.review;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record CreateReviewDTO(@NotNull @Min(1) @Max(5) int rating,
                              @NotBlank @Length(max = 500) String comment) {
}
