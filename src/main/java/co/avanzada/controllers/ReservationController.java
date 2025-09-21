package co.avanzada.controllers;

import co.avanzada.dtos.reservs.CreateReserveDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Reserves")
public class ReservationController {
    @PostMapping()
    public ResponseEntity <String> CreateReserve(@RequestBody CreateReserveDTO createReserve){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping()
    public ResponseEntity<String> getReservations(@RequestParam (required = false) String estado,
                                                  @RequestParam (required = false) String checkIn,
                                                  @RequestParam (required = false) String checkOut,
                                                  @RequestParam (required = false) String page,
                                                  @RequestParam (required = false) String pageSize){

        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getReservation(@PathVariable String id){
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable String id){
        return ResponseEntity.ok().build();
    }
    @GetMapping("/listings/{id}")
    public ResponseEntity<String> getListingsByReservation(@PathVariable String id){

        return ResponseEntity.ok().build();
    }
}
