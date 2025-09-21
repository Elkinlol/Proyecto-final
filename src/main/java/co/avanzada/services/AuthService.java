package co.avanzada.services;

import co.avanzada.dtos.ResponseDTO;
import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.LoginUserDTO;
import co.avanzada.dtos.user.UserDTO;


public interface AuthService {

    CreateUserDTO createUser(CreateUserDTO createUserDTO);
    Void loginUser( LoginUserDTO loginUserDTO);
    String requestResetPassword( RequestResetPasswordDTO resetPasswordDTO);
    String resetPassword( ResetPasswordDTO resetPasswordDTO);

}
