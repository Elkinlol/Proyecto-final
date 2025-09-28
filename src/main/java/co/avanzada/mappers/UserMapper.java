package co.avanzada.mappers;

import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.UserDTO;
import co.avanzada.model.User;
import co.avanzada.model.enunms.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

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

    User toEntity(CreateUserDTO createUserDTO);


    UserDTO toUserDTO(User user);
}
