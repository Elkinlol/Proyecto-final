package co.avanzada.services.impl;

import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.LoginUserDTO;
import co.avanzada.dtos.user.UserDTO;
import co.avanzada.exception.ConflictException;
import co.avanzada.exception.UnatorizedException;
import co.avanzada.mappers.ResetPasswordCodeMapper;
import co.avanzada.mappers.UserMapper;

import co.avanzada.model.PasswordResetCode;
import co.avanzada.model.User;
import co.avanzada.repository.ResetPasswordRepository;
import co.avanzada.repository.UserRepository;
import co.avanzada.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ResetPasswordCodeMapper resetPasswordCodeMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;



    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO)  {
        User user =userMapper.toEntity(createUserDTO);
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ConflictException("Este email ya existe");
        }
        String password = encode(createUserDTO.password());
        user.setPassword(password);
        userRepository.save(user);

        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO loginUser(LoginUserDTO loginUserDTO) {
        User user = validateEmail(loginUserDTO.email());
        if(!passwordEncoder.matches(loginUserDTO.password(),user.getPassword())){
            throw new UnatorizedException("Creedenciales incorrectas");
        }
        return userMapper.toUserDTO(user);
    }

    @Override
    public String requestResetPassword(RequestResetPasswordDTO resetPasswordDTO) {
        return "";
    }

    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = validateEmail(resetPasswordDTO.email());
        String code = generateRestCode();
        PasswordResetCode passwordResetCode = resetPasswordCodeMapper.toEntity(resetPasswordDTO);
        passwordResetCode.setCode(code);
        resetPasswordRepository.save(code);
        emailService.send(user.getEmail(), "El codigo de recuperacion es: "+ code);
        return  passwordResetCode.getCode();
    }



    private String encode(String password){
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private  User validateEmail(String email){
        if(!userRepository.findByEmail(email).isPresent()){
            throw new ConflictException("Correo no registrado");
        }
        User user = userRepository.findByEmail(email).get();
        return user;
    }

    private String generateRestCode(){
        String code = String.format("%05d", new Random().nextInt(100000));
        return code;
    }
}
