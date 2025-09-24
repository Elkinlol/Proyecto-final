package co.avanzada.mappers;

import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.UserDTO;
import co.avanzada.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "rol", constant = "GUEST")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")

    User toEntity(CreateUserDTO createUserDTO);
    UserDTO toUserDTO(User user);
}
