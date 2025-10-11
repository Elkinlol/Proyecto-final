package co.avanzada.services.impl;

import co.avanzada.dtos.promotionDTO.CreatePromotionDTO;
import co.avanzada.dtos.promotionDTO.PromotionDTO;
import co.avanzada.exception.ForbiddenException;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.mappers.PromotionMapper;
import co.avanzada.model.Listing;
import co.avanzada.model.Promotion;
import co.avanzada.model.User;
import co.avanzada.repository.ListingRepository;
import co.avanzada.repository.PromotionRepository;
import co.avanzada.repository.UserRepository;
import co.avanzada.security.AuthUtils;
import co.avanzada.services.PromotionService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class PromotionServiceImpl implements PromotionService {
    private final  PromotionRepository promotionRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final PromotionMapper promotionMapper;
    private final AuthUtils authUtils;


    @Override
    public PromotionDTO createPromotion(String id, CreatePromotionDTO createPromotionDTO) {
        String idUser = authUtils.getCurrentUserId();
        getUserById(idUser);
        Listing listing = findListingById(id);
        validateListingOwner(listing, idUser);
        Promotion promotion = promotionMapper.toEntity(createPromotionDTO);
        promotion.setListing(listing);
        promotionRepository.save(promotion);
        return promotionMapper.toDTO(promotion);
    }

    @Override
    public PromotionDTO getPromotions(Long id) {
        String idUser = authUtils.getCurrentUserId();
        getUserById(idUser);
        Promotion promotion = findPromotionById(id);
        return promotionMapper.toDTO(promotion);
    }

    private Promotion findPromotionById(Long id){
        if(!promotionRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("No se encontro la promocion buscada");
        }
        return  promotionRepository.findById(id).get();
    }
    private void validateListingOwner (Listing listing, String idUser)  {
        if(!listing.getHost().getId().equals(idUser)){
            throw new ForbiddenException("Este host no es el dueño del alojamiento");
        }

    }

    private Listing findListingById(String id){
        if(!listingRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Alojamiento no encontrado");
        }
        return listingRepository.findById(id).get();
    }

    private User getUserById(String id) {
        if(!userRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return userRepository.findById(id).get();
    }
}
