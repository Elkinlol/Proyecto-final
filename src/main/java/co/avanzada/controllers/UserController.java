package co.avanzada.controllers;


import co.avanzada.dtos.user.UpdatePasswordDTO;
import co.avanzada.dtos.user.UpdateProfileDTO;
import co.avanzada.dtos.user.UserDTO;
import co.avanzada.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity <String> updatePassword(@RequestBody UpdatePasswordDTO request, @PathVariable String id ){
        userService.updatePassword(request,id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/")
    public ResponseEntity <UserDTO> updateUser (@RequestBody UpdateProfileDTO updateProfileDTO, @PathVariable String id) {
        userService.updateUser(updateProfileDTO, id);
        return ResponseEntity.ok().build();}

    @GetMapping("/{id}")
    public ResponseEntity <UserDTO> findUserById(@PathVariable String id){
        userService.findUserById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <String> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
