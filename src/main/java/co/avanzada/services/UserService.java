package co.avanzada.services;

import co.avanzada.dtos.user.UpdatePasswordDTO;
import co.avanzada.dtos.user.UpdateProfileDTO;
import co.avanzada.model.User;

public interface UserService {

    Void updateUser ( UpdateProfileDTO updateProfileDTO, String id);
    User findUserById( String id);
    Void updatePassword( UpdatePasswordDTO request,  String id );
}
