package co.avanzada.dtos.review;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record CreateReviewDTO(@NotNull @DecimalMin("1") @DecimalMax("5") BigDecimal rating,
                              @NotBlank @Length(max = 500) String comment) {
}
