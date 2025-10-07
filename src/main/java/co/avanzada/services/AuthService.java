package co.avanzada.services;

import co.avanzada.dtos.auth.LoginResponseDTO;
import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.LoginUserDTO;
import co.avanzada.dtos.user.UserDTO;


public interface AuthService {

    UserDTO createUser(CreateUserDTO createUserDTO);
    LoginResponseDTO loginUser(LoginUserDTO loginUserDTO);
    void requestResetPassword( RequestResetPasswordDTO resetPasswordDTO);
    String resetPassword( ResetPasswordDTO resetPasswordDTO);

}
