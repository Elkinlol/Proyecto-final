package co.avanzada.services;

import co.avanzada.dtos.promotionDTO.CreatePromotionDTO;
import co.avanzada.dtos.promotionDTO.PromotionDTO;


public interface PromotionService {
    PromotionDTO createPromotion(String id, CreatePromotionDTO createPromotionDTO);
    PromotionDTO getPromotions( Long id);

}
