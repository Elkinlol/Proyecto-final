package co.avanzada.controllers;

import co.avanzada.dtos.ResponseDTO;
import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.LoginUserDTO;
import co.avanzada.services.AuthService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
public class AuthController {

    AuthService authService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> createUser(@RequestBody CreateUserDTO createUserDTO){
        String message= authService.createUser(createUserDTO);
        return ResponseEntity.ok(new ResponseDTO<>(false, message));
    }


    @PostMapping("/Login")
    public ResponseEntity<ResponseDTO<String>> loginUser(@Valid @RequestBody LoginUserDTO loginUserDTO){
        authService.loginUser(loginUserDTO);
        return ResponseEntity.ok().build();
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
