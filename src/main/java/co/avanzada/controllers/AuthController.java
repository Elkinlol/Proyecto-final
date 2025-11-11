package co.avanzada.controllers;

import co.avanzada.dtos.auth.LoginResponseDTO;
import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.dtos.extras.ResponseUserDTO;
import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.LoginUserDTO;
import co.avanzada.dtos.user.UserDTO;
import co.avanzada.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ResponseUserDTO<UserDTO>> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
        UserDTO userDTO= authService.createUser(createUserDTO);
        ResponseUserDTO responseDTO= new ResponseUserDTO(true, " Usuario creado con exito", userDTO );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseUserDTO<LoginResponseDTO>> loginUser(@Valid @RequestBody LoginUserDTO loginUserDTO){
        LoginResponseDTO loginResponseDTO= authService.loginUser(loginUserDTO);
        ResponseUserDTO responseDTO = new ResponseUserDTO<>(true, "Se ha iniciado sesion",  loginResponseDTO);
        return ResponseEntity.ok().body(responseDTO);
    }
    @PostMapping("/password")
    public ResponseEntity<ResponseDTO<String>> requestResetPassword(@Valid @RequestBody RequestResetPasswordDTO resetPasswordDTO){
        authService.requestResetPassword(resetPasswordDTO);
        return ResponseEntity.ok().body(new ResponseDTO<>(true, "Contraseña cambiada"));
    }
    @PutMapping("/change/password")
    public ResponseEntity<ResponseDTO<String>> resetPassword(@Valid  @RequestBody ResetPasswordDTO resetPasswordDTO){
        authService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok().body(new ResponseDTO<>(true, "codigo enviado"));
    }

}
