package co.avanzada.services;

import co.avanzada.dtos.user.*;
import co.avanzada.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserDTO updateUser (UpdateProfileDTO updateProfileDTO);
    UserDTO findUserById( );
    Void updatePassword( UpdatePasswordDTO request );
    Void deleteUser( );
    String  upgradeToHost();
    HostDTO findHost();
    HostDTO updateHost(UpdateProfileHostDTO updateProfileHostDTO );
    String upgradeToGuest();
    String uploadProfilePhoto(String userId, MultipartFile image)throws Exception;
}
