package co.avanzada.services.impl;

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
import co.avanzada.services.UserService;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequiredArgsConstructor
@Builder
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;
    private final ImageService imageService;
    private final JWTUtils jwtUtils;


    @Override
    public UserDTO updateUser(UpdateProfileDTO updateProfileDTO) {
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);
        userMapper.updateUserFromDTO(updateProfileDTO, user);
        userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO findUserById() {
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);
        return userMapper.toUserDTO(user);
    }

    @Override
    public Void updatePassword(UpdatePasswordDTO updatePasswordDTO){
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);
        if (!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new ConflictException("La contraseña actual no coincide con la base de datos");
        }
        if(passwordEncoder.matches(updatePasswordDTO.getNewPassword(), user.getPassword())){
            throw new ConflictException("La contraseña es repetida");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        userRepository.save(user);
        return null;
    }

    @Override
    public Void deleteUser() {
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);
        user.setStatus(Status.INACTIVE);
        userRepository.save(user);
        return null;
    }
    @Override
    public String upgradeToHost() {
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);
        if (user.getIsHost()) {
            throw new ConflictException("El usuario ya es host");
        }
        user.setIsHost(true);
        if (!user.getRol().equals(Rol.HOST)) {
            user.setRol(Rol.HOST);
        }
        String token = jwtUtils.generateToken(user.getId(), Map.of("rol", user.getRol().name()));
        userRepository.save(user);
        return token;
    }

    @Override
    public HostDTO findHost() {
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);
        if (!user.getIsHost()){
            throw new UnatorizedException("El usuario no cuenta con esos credenciales");
        }
        return userMapper.toHostDTO(user);

    }

    @Override
    public HostDTO updateHost(UpdateProfileHostDTO updateProfileHostDTO) {
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);
        if (!user.getIsHost()) {
            throw new UnatorizedException("El usuario no tiene permisos de host");
        }
        userMapper.updateHostFromDTO(updateProfileHostDTO, user);
        userRepository.save(user);
        return userMapper.toHostDTO(user);
    }

    @Override
    public String uploadProfilePhoto(String userId, MultipartFile image) throws Exception {
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);


        // Eliminar la imagen anterior si existía
        if (user.getProfilePhotoId() != null) {
            imageService.delete(user.getProfilePhotoId());
        }

        Map uploadResult = imageService.upload(image);
        user.setProfilePhoto((String) uploadResult.get("secure_url"));
        user.setProfilePhotoId((String) uploadResult.get("public_id"));
        userRepository.save(user);

        return user.getProfilePhoto();
    }

    @Override
    public String upgradeToGuest() {
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);
        if (!user.getIsHost()) {
            throw new ConflictException("El usuario ya es guest");
        }
        user.setIsHost(false);
        user.setRol(Rol.GUEST);
        String token = jwtUtils.generateToken(user.getId(), Map.of("rol", user.getRol().name()));
        userRepository.save(user);
        return token;
    }


    private User getUserById(String id) {
        if(!userRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return userRepository.findById(id).get();
    }



}
