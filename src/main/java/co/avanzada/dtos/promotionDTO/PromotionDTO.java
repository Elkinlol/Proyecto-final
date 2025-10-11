package co.avanzada.dtos.promotionDTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromotionDTO(@NotNull LocalDateTime createdAt,
                           @NotNull LocalDateTime expirationDate,
                           @NotNull @DecimalMin("0.0") @DecimalMax("100.0") BigDecimal promotion) {
}
