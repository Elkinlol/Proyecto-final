package co.avanzada.services;

import co.avanzada.dtos.promotionDTO.CreatePromotionDTO;



public interface PromotionService {
    void createPromotion( String id,  CreatePromotionDTO createPromotionDTO);
    void getPromotions( String id);

}
