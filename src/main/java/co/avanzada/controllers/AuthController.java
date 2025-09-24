package co.avanzada.controllers;

import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
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
    public ResponseEntity<ResponseDTO<UserDTO>> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
        UserDTO userDTO= authService.createUser(createUserDTO);
        ResponseDTO responseDTO= new ResponseDTO(true, userDTO );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);//Falta la respuesta 409
    }
    @PostMapping("/Login")
    public ResponseEntity<ResponseDTO<UserDTO>> loginUser(@Valid @RequestBody LoginUserDTO loginUserDTO){

        UserDTO userDTO = authService.loginUser(loginUserDTO);
        return ResponseEntity.ok().body(new ResponseDTO(true, userDTO));
    }
    @PostMapping("Password")
    public ResponseEntity<ResponseDTO<String>> requestResetPassword(@Valid @RequestBody RequestResetPasswordDTO resetPasswordDTO){
        authService.requestResetPassword(resetPasswordDTO);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/Change/Password")
    public ResponseEntity<ResponseDTO<String>> resetPassword(@Valid  @RequestBody ResetPasswordDTO resetPasswordDTO){
        authService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok().build();
    }

}
