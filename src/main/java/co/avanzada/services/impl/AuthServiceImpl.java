package co.avanzada.services.impl;

import co.avanzada.dtos.auth.LoginResponseDTO;
import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.LoginUserDTO;
import co.avanzada.dtos.user.UserDTO;
import co.avanzada.exception.ConflictException;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.exception.UnatorizedException;
import co.avanzada.mappers.ResetPasswordCodeMapper;
import co.avanzada.mappers.UserMapper;

import co.avanzada.model.PasswordResetCode;
import co.avanzada.model.User;
import co.avanzada.model.enunms.Status;
import co.avanzada.repository.ResetPasswordRepository;
import co.avanzada.repository.UserRepository;
import co.avanzada.security.JWTUtils;
import co.avanzada.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final ResetPasswordCodeMapper resetPasswordCodeMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final JWTUtils jwtUtils;



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
    public LoginResponseDTO  loginUser(LoginUserDTO loginUserDTO) {
        User user = validateEmail(loginUserDTO.email());
        if(!passwordEncoder.matches(loginUserDTO.password(),user.getPassword())){
            throw new UnatorizedException("Creedenciales incorrectas");
        }
        if(user.getStatus().equals(Status.INACTIVE)){
            throw new UnatorizedException("Cuenta inactiva");
        }
        String token = jwtUtils.generateToken(user.getId(), Map.of("rol", user.getRol().name()));
        return  new LoginResponseDTO (userMapper.toUserDTO(user), token) ;
    }

    @Override
    public void requestResetPassword(RequestResetPasswordDTO resetPasswordDTO) {
        User user = validateEmail(resetPasswordDTO.email());
        Optional<PasswordResetCode> passwordResetCode= resetPasswordRepository.findByUserAndCode(user, resetPasswordDTO.recuperationCode());
        if(!passwordResetCode.isPresent()){
            throw new UnatorizedException("El codigo enviado es erroneo");
        }
        LocalDateTime now = LocalDateTime.now();
        PasswordResetCode code = passwordResetCode.get();
        if(code.getExpiresAt().isBefore(now)){
            throw new ConflictException("El codigo enviado ha expirado, solicitar uno nuevo");
        }
        if(passwordEncoder.matches(resetPasswordDTO.newPassword(), user.getPassword())){
            throw new UnatorizedException("Contraseña igual a la anterior");
        }
        String password = encode(resetPasswordDTO.newPassword());
        user.setPassword(password);
        userRepository.save(user);
        resetPasswordRepository.delete(code);
        return ;
    }

    @Override
    public String resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = validateEmail(resetPasswordDTO.email());
        String code = generateRestCode();
        PasswordResetCode passwordResetCode = resetPasswordCodeMapper.toEntity(user, code);
        passwordResetCode.setCode(code);
        resetPasswordRepository.save(passwordResetCode);
        try {
            emailService.send(user.getEmail(), "El código de recuperación es: " + code);
        } catch (Exception e) {
            // Loguea el error, pero no interrumpe la ejecución
            log.error("No se pudo enviar el correo a " + user.getEmail(), e);
        }
        return  passwordResetCode.getCode();
    }



    private String encode(String password){
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private  User validateEmail(String email){
        if(!userRepository.findByEmail(email).isPresent()){
            throw new UnatorizedException("Correo no registrado");
        }
        User user = userRepository.findByEmail(email).get();
        return user;
    }

    private String generateRestCode(){
        String code = String.format("%05d", new Random().nextInt(100000));
        return code;
    }
}
