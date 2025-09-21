package co.avanzada.services.impl;

import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.LoginUserDTO;
import co.avanzada.exception.ConflictException;
import co.avanzada.mappers.UserMapper;
import co.avanzada.model.Rol;
import co.avanzada.model.Status;
import co.avanzada.model.User;
import co.avanzada.services.AuthService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor



@Service
public class AuthServiceImpl implements AuthService {

    UserMapper userMapper;
    @Override
    public CreateUserDTO createUser(CreateUserDTO createUserDTO)  {
        if(IsEmailDuplicated(createUserDTO.email())){
            throw new ConflictException ("Correo electronico asociado a otra cuenta");
        }
        String password = encode(createUserDTO.password());
        User user =userMapper.toEntity(createUserDTO);
        user.setPassword(password);
        user.setCreateAT(LocalDateTime.now());
        user.setStatus(Status.ACTIVE);
        user.setRol(Rol.GUEST);
        //User saveUser= userRepository.save(user);
        return createUserDTO;
    }

    @Override
    public Void loginUser(LoginUserDTO loginUserDTO) {

        return null;
    }

    @Override
    public String requestResetPassword(RequestResetPasswordDTO resetPasswordDTO) {
        return "";
    }

    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        return "";
    }

    private String encode(String password){
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private boolean IsEmailDuplicated(Email email){
        if("".equals(email)){
            return true;
        }
        return false;
    }
}
