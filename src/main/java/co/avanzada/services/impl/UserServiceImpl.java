package co.avanzada.services.impl;

import co.avanzada.dtos.user.UpdatePasswordDTO;
import co.avanzada.dtos.user.UpdateProfileDTO;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.mappers.UserMapper;
import co.avanzada.model.User;
import co.avanzada.model.enunms.Status;
import co.avanzada.repository.UserRepository;
import co.avanzada.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Builder
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public Void updateUser(UpdateProfileDTO updateProfileDTO, String id) {
        User user = getUserById(id);
        userMapper.updateUserFromDTO(updateProfileDTO, user);
        userRepository.save(user);
        return null;
    }

    @Override
    public User findUserById(String id) {
        return getUserById(id);
    }

    @Override
    public Void updatePassword(UpdatePasswordDTO updatePasswordDTO, String id){
        return null;
    }

    @Override
    public Void deleteUser( String id) {
        User user = getUserById(id);
        user.setStatus(Status.INACTIVE);
        userRepository.save(user);
        return null;
    }

    private User getUserById(String id) {
        if(!userRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return userRepository.findById(id).get();
    }
}
