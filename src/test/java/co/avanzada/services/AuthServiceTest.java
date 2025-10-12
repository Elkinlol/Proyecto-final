package co.avanzada.services;

import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.LoginUserDTO;
import co.avanzada.exception.ConflictException;
import co.avanzada.exception.UnatorizedException;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.model.User;
import co.avanzada.model.enunms.Rol;
import co.avanzada.model.enunms.Status;
import co.avanzada.repository.UserRepository;
import co.avanzada.repository.ResetPasswordRepository;
import co.avanzada.services.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    private AuthServiceImpl authService;

    @MockitoBean
    private JavaMailSender mailSender; // mockeado

    private User existingUser;

    @BeforeEach
    void setup() {
        resetPasswordRepository.deleteAll();
        userRepository.deleteAll();

        existingUser = new User();
        existingUser.setId("u001");
        existingUser.setFullName("María Gómez");
        existingUser.setEmail("maria.gomez@example.com");
        existingUser.setPassword("1234"); // contraseña simple para test
        existingUser.setNumberPhone("+573001112233");
        existingUser.setBirthday(LocalDate.of(1990, 6, 15));
        existingUser.setCreatedAt(LocalDateTime.now());
        existingUser.setRol(Rol.GUEST);
        existingUser.setStatus(Status.ACTIVE);
        existingUser.setProfilePhoto("https://example.com/photos/maria.jpg");
        existingUser.setIsHost(false);

        userRepository.save(existingUser);
    }

    // ------------------- CREATE USER -------------------
    @Test
    void testCreateUser() {
        var newUser = new CreateUserDTO(
                "Carlos",
                "123456789",
                "carlos@email.com",
                "Password123",
                LocalDate.now(),
                Rol.GUEST
        );

        assertDoesNotThrow(() -> authService.createUser(newUser));
    }

    @Test
    void testCreateUserThrowsConflictExceptionWhenEmailExists() {
        var newUser = new CreateUserDTO(
                existingUser.getFullName(),
                existingUser.getNumberPhone(),
                existingUser.getEmail(), // mismo email
                "Password123",
                LocalDate.now(),
                Rol.GUEST
        );

        assertThrows(ConflictException.class, () -> authService.createUser(newUser));
    }

    // ------------------- LOGIN -------------------
    @Test
    void testLoginUser() {
        assertDoesNotThrow(() ->
                authService.loginUser(new LoginUserDTO(existingUser.getEmail(), existingUser.getPassword()))
        );
    }

    @Test
    void testLoginUserThrowsExceptionWhenEmailDoesNotExist() {
        assertThrows(UnatorizedException.class, () ->
                authService.loginUser(new LoginUserDTO("noexiste@email.com", existingUser.getPassword()))
        );
    }

    @Test
    void testLoginUserThrowsExceptionWhenPasswordDoesNotMatch() {
        assertThrows(UnatorizedException.class, () ->
                authService.loginUser(new LoginUserDTO(existingUser.getEmail(), "contraseña_incorrecta"))
        );
    }

    // ------------------- RESET PASSWORD -------------------
    @Test
    void testRequestResetPasswordSucceeds() {
        // Genera código de recuperación
        String code = authService.resetPassword(new ResetPasswordDTO(existingUser.getEmail()));

        var resetDTO = new RequestResetPasswordDTO(
                existingUser.getEmail(),
                code,
                "NuevaPassword123"
        );

        assertDoesNotThrow(() -> authService.requestResetPassword(resetDTO));
    }

    @Test
    void testRequestResetPasswordThrowsUnatorizedWhenEmailNotRegistered() {
        var resetDTO = new RequestResetPasswordDTO(
                "noexiste@email.com",
                "codigo123",
                "NuevaPassword123"
        );

        assertThrows(UnatorizedException.class, () -> authService.requestResetPassword(resetDTO));
    }

    @Test
    void testResetPasswordThrowsWhenCodeInvalid() {
        var resetDTO = new RequestResetPasswordDTO(
                existingUser.getEmail(),
                "codigo_incorrecto",
                "NuevaPassword123"
        );

        assertThrows(UnatorizedException.class, () -> authService.requestResetPassword(resetDTO));
    }

    @Test
    void testResetPasswordThrowsWhenPasswordSameAsOld() {
        String code = authService.resetPassword(new ResetPasswordDTO(existingUser.getEmail()));

        var resetDTO = new RequestResetPasswordDTO(
                existingUser.getEmail(),
                code,
                existingUser.getPassword() // misma que la actual
        );

        assertThrows(UnatorizedException.class, () -> authService.requestResetPassword(resetDTO));
    }

}
