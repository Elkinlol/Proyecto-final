package co.avanzada.services.impl;

import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.LoginUserDTO;
import co.avanzada.dtos.user.UserDTO;
import co.avanzada.exception.ConflictException;
import co.avanzada.exception.UnatorizedException;
import co.avanzada.mappers.UserMapper;

import co.avanzada.model.User;
import co.avanzada.repository.UserRepository;
import co.avanzada.services.AuthService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO)  {
        User user =userMapper.toEntity(createUserDTO);
        if(userRepository.findByEmail(user.getEmail())!=null){
            new ConflictException("Este email ya existe");
        }

        String password = encode(createUserDTO.password());
        user.setPassword(password);
        userRepository.save(user);
        UserDTO userDTO = userMapper.toUserDTO(user);
        return userDTO;
    }

    @Override
    public UserDTO loginUser(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByEmail(loginUserDTO.email())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException("Usuario no encontrado"));*/
        if(!passwordEncoder.matches(loginUserDTO.password(),user.getPassword())){
            throw new UnatorizedException("Contraseña incorrecta");
        }

        UserDTO userDTO = userMapper.toUserDTO(user);

        return userDTO;
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
