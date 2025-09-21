package co.avanzada.controllers;

import co.avanzada.dtos.ResponseDTO;
import co.avanzada.dtos.reservs.CreateReserveDTO;
import co.avanzada.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Reserves")
@RequiredArgsConstructor
public class ReservationController {

    ReservationService reservationService;

    @PostMapping()
    public ResponseEntity <ResponseDTO<String>> CreateReserve(@Valid @RequestBody CreateReserveDTO createReserveDTO){
        reservationService.CreateReserve(createReserveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping()
    public ResponseEntity<ResponseDTO<ResponseDTO>> getReservations(@RequestParam (required = false) String estado,
                                                  @RequestParam (required = false) String checkIn,
                                                  @RequestParam (required = false) String checkOut,
                                                  @RequestParam (required = false) String page,
                                                  @RequestParam (required = false) String pageSize){

        reservationService.getReservations(estado,checkIn,checkOut,page,pageSize);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> getReservation(@PathVariable String id){
        reservationService.getReservation(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteReservation(@PathVariable String id){
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/listings/{id}")
    public ResponseEntity<ResponseDTO<String>> getListingsByReservation(@PathVariable String id){
        reservationService.getListingsByReservation(id);
        return ResponseEntity.ok().build();
    }
}
