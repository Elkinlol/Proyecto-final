package co.avanzada.mappers;

import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.model.PasswordResetCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper (componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResetPasswordCodeMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "expiresAt", expression = "java(java.time.LocalDateTime.now().plusMinutes(15))")
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")

    PasswordResetCode toEntity(ResetPasswordDTO resetPasswordDTO);

    RequestResetPasswordDTO toDTO(PasswordResetCode entity);

}
