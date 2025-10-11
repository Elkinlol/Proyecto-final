package co.avanzada.mappers;

import co.avanzada.dtos.promotionDTO.CreatePromotionDTO;
import co.avanzada.dtos.promotionDTO.PromotionDTO;
import co.avanzada.model.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PromotionMapper {

    @Mapping(target = "promotion", source =  "promotion")
    Promotion toEntity (CreatePromotionDTO dto);

    @Mapping(target = "promotion", source =  "promotion")
    PromotionDTO toDTO (Promotion promotion);
}
