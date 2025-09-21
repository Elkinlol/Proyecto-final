package co.avanzada.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatePromotionDTO(@NotNull LocalDateTime dateStart,
                                 @NotNull LocalDateTime dateEnd,
                                 @NotNull @DecimalMin("0.0") @DecimalMax("100.0") BigDecimal promotion) {
}
