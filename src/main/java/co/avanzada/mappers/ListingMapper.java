package co.avanzada.mappers;

import co.avanzada.dtos.listings.CreateListingDTO;
import co.avanzada.dtos.listings.ListingDTO;
import co.avanzada.dtos.listings.UpdateListingDTO;
import co.avanzada.model.Listing;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListingMapper {


    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", constant = "ACTIVE")
    Listing toEntity(CreateListingDTO dto);


    ListingDTO toDTO(Listing entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateListingFromDTO(UpdateListingDTO dto, @MappingTarget Listing entity);
}
