package co.avanzada.controllers;

import co.avanzada.dtos.auth.RequestResetPasswordDTO;
import co.avanzada.dtos.auth.ResetPasswordDTO;
import co.avanzada.dtos.user.CreateUserDTO;
import co.avanzada.dtos.user.LoginUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Auth")
public class AuthController {

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserDTO createUserDTO){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/Login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        return ResponseEntity.ok().build();
    }
    @PostMapping("Password")
    public ResponseEntity<String> requestResetPassword(@RequestBody RequestResetPasswordDTO resetPasswordDTO){
        return ResponseEntity.ok().build();
    }
    @PutMapping("/Change/Password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        return ResponseEntity.ok().build();
    }

}
