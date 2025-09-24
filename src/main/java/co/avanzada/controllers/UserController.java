package co.avanzada.controllers;


import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.user.UpdatePasswordDTO;
import co.avanzada.dtos.user.UpdateProfileDTO;
import co.avanzada.dtos.user.UserDTO;
import co.avanzada.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PatchMapping("/{id}/password")
    public ResponseEntity <ResponseDTO<String>> updatePassword(@Valid @RequestBody UpdatePasswordDTO request, @PathVariable String id ){
        userService.updatePassword(request,id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/")
    public ResponseEntity <ResponseDTO<UserDTO>> updateUser (@Valid @RequestBody UpdateProfileDTO updateProfileDTO, @PathVariable String id) {
        userService.updateUser(updateProfileDTO, id);
        return ResponseEntity.ok().build();}

    @GetMapping("/{id}")
    public ResponseEntity <ResponseDTO<UserDTO>> findUserById(@PathVariable String id){
        userService.findUserById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <ResponseDTO<String>> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
