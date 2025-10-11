package co.avanzada.mappers;

import co.avanzada.dtos.reservs.CreateReserveDTO;
import co.avanzada.dtos.reservs.ReservDTO;
import co.avanzada.model.Reservations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "guestCount", source = "guestCount")
    @Mapping(target = "checkIn", source = "checkIn")
    @Mapping(target = "checkOut", source = "checkOut")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Reservations toEntity(CreateReserveDTO dto);


    @Mapping(target = "guestCount", source = "guestCount")
    @Mapping(target = "status", source = "reservationsStatus")
    @Mapping(target = "totalPrice", source = "price")
    ReservDTO toDTO(Reservations entity);
}
