package co.avanzada.dtos.user;

import co.avanzada.model.Rol;
import jakarta.validation.constraints.Email;

import java.util.Date;

public record UserDTO(String name, Email email, String password, String phone, Date dateBirth,
                      Rol rol, String pthoto) {
}
