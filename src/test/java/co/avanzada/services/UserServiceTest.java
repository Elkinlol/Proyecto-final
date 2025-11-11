package co.avanzada.services;

import co.avanzada.dtos.user.*;
import co.avanzada.exception.ConflictException;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.exception.UnatorizedException;
import co.avanzada.mappers.UserMapper;
import co.avanzada.model.User;
import co.avanzada.model.enunms.Rol;
import co.avanzada.model.enunms.Status;
import co.avanzada.repository.UserRepository;
import co.avanzada.security.AuthUtils;
import co.avanzada.security.JWTUtils;
import co.avanzada.services.ImageService;
import co.avanzada.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class  UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthUtils authUtils;
    @Mock
    private ImageService imageService;
    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id("123")
                .rol(Rol.GUEST)
                .isHost(false)
                .status(Status.ACTIVE)
                .password("encodedPassword")
                .build();

        when(authUtils.getCurrentUserId()).thenReturn("123");
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
    }

    @Test
    void updateUser_shouldReturnUpdatedUserDTO() {
        UpdateProfileDTO dto = new UpdateProfileDTO();
        UserDTO userDTO = userMapper.toUserDTO(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.updateUser(dto);

        verify(userMapper).updateUserFromDTO(dto, user);
        verify(userRepository).save(user);
        assertEquals(userDTO, result);
    }

    @Test
    void findUserById_shouldReturnUserDTO() {
        UserDTO userDTO = userMapper.toUserDTO(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.findUserById();

        assertEquals(userDTO, result);
    }

    @Test
    void updatePassword_shouldThrowConflictException_ifOldPasswordDoesNotMatch() {
        UpdatePasswordDTO dto = new UpdatePasswordDTO();
        dto.setOldPassword("wrongPassword");
        dto.setNewPassword("newPassword");

        when(passwordEncoder.matches(dto.getOldPassword(), user.getPassword())).thenReturn(false);

        assertThrows(ConflictException.class, () -> userService.updatePassword(dto));
    }

    @Test
    void updatePassword_shouldThrowConflictException_ifNewPasswordSameAsOld() {
        UpdatePasswordDTO dto = new UpdatePasswordDTO();
        dto.setOldPassword("old");
        dto.setNewPassword("old");

        when(passwordEncoder.matches("old", user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches("old", user.getPassword())).thenReturn(true);

        assertThrows(ConflictException.class, () -> userService.updatePassword(dto));
    }

    @Test
    void updatePassword_shouldUpdatePassword() {
        UpdatePasswordDTO dto = new UpdatePasswordDTO();
        dto.setOldPassword("old");
        dto.setNewPassword("new");

        when(passwordEncoder.matches("old", user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches("new", user.getPassword())).thenReturn(false);
        when(passwordEncoder.encode("new")).thenReturn("encodedNew");

        userService.updatePassword(dto);

        assertEquals("encodedNew", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void deleteUser_shouldSetStatusInactive() {
        userService.deleteUser();

        assertEquals(Status.INACTIVE, user.getStatus());
        verify(userRepository).save(user);
    }

    @Test
    void upgradeToHost_shouldSetUserAsHostAndReturnToken() {
        user.setIsHost(false);
        user.setRol(Rol.GUEST);

        when(jwtUtils.generateToken(eq(user.getId()), anyMap())).thenReturn("token123");

        String token = userService.upgradeToHost();

        assertTrue(user.getIsHost());
        assertEquals(Rol.HOST, user.getRol());
        assertEquals("token123", token);
        verify(userRepository).save(user);
    }

    @Test
    void upgradeToHost_shouldThrowConflict_ifAlreadyHost() {
        user.setIsHost(true);

        assertThrows(ConflictException.class, () -> userService.upgradeToHost());
    }

    @Test
    void findHost_shouldReturnHostDTO() {
        user.setIsHost(true);
        HostDTO hostDTO = userMapper.toHostDTO(user);
        when(userMapper.toHostDTO(user)).thenReturn(hostDTO);

        HostDTO result = userService.findHost();

        assertEquals(hostDTO, result);
    }

    @Test
    void findHost_shouldThrowUnatorized_ifNotHost() {
        user.setIsHost(false);
        assertThrows(UnatorizedException.class, () -> userService.findHost());
    }

    @Test
    void uploadProfilePhoto_shouldUploadAndReturnUrl() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        Map<String, String> uploadResult = new HashMap<>();
        uploadResult.put("secure_url", "url");
        uploadResult.put("public_id", "id");

        when(imageService.upload(file)).thenReturn(uploadResult);

        String url = userService.uploadProfilePhoto(file);

        assertEquals("url", url);
        assertEquals("url", user.getProfilePhoto());
        assertEquals("id", user.getProfilePhotoId());
        verify(userRepository).save(user);
    }
}
