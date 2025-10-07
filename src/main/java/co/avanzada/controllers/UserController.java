package co.avanzada.controllers;


import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.extras.ResponseUserDTO;
import co.avanzada.dtos.user.*;
import co.avanzada.model.Host;
import co.avanzada.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/password")
    public ResponseEntity <ResponseDTO<String>> updatePassword(@Valid @RequestBody UpdatePasswordDTO request ){
        userService.updatePassword(request);

        return ResponseEntity.ok().body(new ResponseDTO<>(false, "Contraseña cambiada con exito"));
    }

    @PutMapping()
    public ResponseEntity <ResponseUserDTO<UserDTO>> updateUser (@Valid @RequestBody UpdateProfileDTO updateProfileDTO) {
        UserDTO userDTO = userService.updateUser(updateProfileDTO);
        return ResponseEntity.ok().body(new ResponseUserDTO<>( false , "Usuario actualizadao con exito", userDTO));
    }

    @GetMapping()
    public ResponseEntity <ResponseUserDTO<UserDTO>> findUserById(){
        UserDTO userDTO = userService.findUserById();
        return ResponseEntity.ok().body(new ResponseUserDTO<>(false, "Se obtuvo el usuario con exito", userDTO));
    }

    @DeleteMapping()
    public ResponseEntity <ResponseDTO<String>> deleteUser(){
        userService.deleteUser();

        return ResponseEntity.ok().body(new ResponseDTO<>( false , "Usuario eliminado con exito"));
    }
    @PatchMapping ("/host")
    public ResponseEntity<ResponseUserDTO<String>> upgradeToHost(){
        String newToken = userService.upgradeToHost();
        return ResponseEntity.ok().body(new ResponseUserDTO<>( false , "Ha cambiado a rol de Anfitrion" , newToken));
    }

    @GetMapping("/host")
    public ResponseEntity <ResponseUserDTO<HostDTO>> findHost(){
        HostDTO host = userService.findHost();
        return ResponseEntity.ok().body(new ResponseUserDTO<>(false, "Se obtuvo el usuario con exito", host));
    }

    @PutMapping("/host")
    public ResponseEntity<ResponseUserDTO<HostDTO>> updateHost(@Valid @RequestBody UpdateProfileHostDTO updateProfileHostDTO) {
        HostDTO host = userService.updateHost(updateProfileHostDTO);
        return ResponseEntity.ok().body(new ResponseUserDTO<>(false, "Usuario actualizado con exito", host));
    }

    @PatchMapping("/guest")
    public ResponseEntity<ResponseDTO<String>> upgradeToGuest(){
        userService.upgradeToGuest();
        return ResponseEntity.ok().body(new ResponseDTO<>( false , "Ha cambiado a rol de huesped"));
    }
}
