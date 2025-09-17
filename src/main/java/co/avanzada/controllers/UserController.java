package co.avanzada.controllers;


import co.avanzada.dtos.UpdatePasswordDTO;
import co.avanzada.dtos.UpdateProfileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Users")
public class UserController {

    @PatchMapping("/{id}/password")
    public ResponseEntity <String> updatePassword(@RequestBody UpdatePasswordDTO request, @PathVariable String id ){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/")
    public ResponseEntity <String> updateUser (@RequestBody UpdateProfileDTO updateProfileDTO, @PathVariable String id) {
        return ResponseEntity.ok().build();}

    @GetMapping("/{id}")
    public ResponseEntity <String> findUserById(@PathVariable String id){
        return ResponseEntity.ok().build();
    }
}
