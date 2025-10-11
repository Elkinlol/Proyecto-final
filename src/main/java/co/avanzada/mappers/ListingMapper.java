package co.avanzada.mappers;


import co.avanzada.dtos.listings.CreateListingDTO;
import co.avanzada.dtos.listings.ListingDTO;
import co.avanzada.dtos.listings.ListingSearchResponseDTO;
import co.avanzada.dtos.listings.UpdateListingDTO;
import co.avanzada.model.Listing;
import org.mapstruct.*;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = { UserMapper.class }
)
public interface ListingMapper {

    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", constant = "ACTIVE")
    Listing toEntity(CreateListingDTO dto);

    @Mapping(target = "host", source = "host") // Usa el mapper de User -> HostDTO
    ListingDTO toDTO(Listing entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateListingFromDTO(UpdateListingDTO dto, @MappingTarget Listing entity);

    @Mapping(target = "city", source = "adress.city")// de Listing.adress.city
    @Mapping(target = "fullName", source = "host.fullName")
    ListingSearchResponseDTO toResponseDTO(Listing entity);


}