package co.avanzada.mappers;

import co.avanzada.dtos.listings.UpdateListingDTO;
import co.avanzada.dtos.user.*;
import co.avanzada.model.Listing;
import co.avanzada.model.User;
import co.avanzada.model.enunms.Rol;
import org.mapstruct.*;

import java.util.Set;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE // Ignora los campos que no existen en el DTO
)
public interface UserMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isHost", expression = "java(false)")
    //@Mapping(target = "rol", expression = "java(Rol.valueOf(dto.Rol)")
    User toEntity(CreateUserDTO createUserDTO);


    UserDTO toUserDTO(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDTO(UpdateProfileDTO dto, @MappingTarget User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "host.aboutMe", source = "aboutMe")
    @Mapping(target = "host.documents", source = "documents")
    void updateHostFromDTO(UpdateProfileHostDTO dto, @MappingTarget User user);

    @Mapping(target = "host.aboutMe", source = "aboutMe")
    @Mapping(target = "host.documents", source = "documents")
    User toEntity(HostDTO dto);

    @Mapping(source = "host.aboutMe", target = "aboutMe")
    @Mapping(source = "host.documents", target = "documents")
    @Mapping(target = "userDTO", expression = "java(toUserDTO(user))")
    HostDTO toHostDTO(User user);
}
