package co.avanzada.services;

import co.avanzada.dtos.CreatePromotionDTO;



public interface PromotionService {
    String createPromotion( String id,  CreatePromotionDTO createPromotionDTO);
    String getPromotions( String id);

}
