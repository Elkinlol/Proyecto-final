package co.avanzada.services.impl;

import co.avanzada.dtos.user.UpdatePasswordDTO;
import co.avanzada.dtos.user.UpdateProfileDTO;
import co.avanzada.model.User;
import co.avanzada.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public Void updateUser(UpdateProfileDTO updateProfileDTO, String id) {
        //logica de acceso a los daatos, vaalidacioes, persistencia....
        return null;
    }

    @Override
    public User findUserById(String id) {
        return null;
    }

    @Override
    public Void updatePassword(UpdatePasswordDTO updatePasswordDTO, String id){
        return null;
    }
}
