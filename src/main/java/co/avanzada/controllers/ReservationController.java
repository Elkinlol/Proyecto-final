package co.avanzada.controllers;

import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.extras.ResponseUserDTO;
import co.avanzada.dtos.reservs.CreateReserveDTO;
import co.avanzada.dtos.reservs.ReservDTO;
import co.avanzada.model.enunms.ReservationStatus;
import co.avanzada.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/reserves")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{listingId}")
    public ResponseEntity <ResponseUserDTO<ReservDTO>> CreateReserve(@Valid @RequestBody CreateReserveDTO createReserveDTO, @PathVariable String listingId) {
        ReservDTO reservDTO = reservationService.CreateReserve(createReserveDTO, listingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUserDTO<>(true, "Reserva creada correctamente", reservDTO));
    }
    @GetMapping()
    public ResponseEntity<ResponseUserDTO<Page<ReservDTO>>> getReservations(@RequestParam (required = false) ReservationStatus estado,
                                                  @RequestParam (required = false) LocalDateTime checkIn,
                                                  @RequestParam (required = false) LocalDateTime checkOut,
                                                  @RequestParam  int page
                                                  ){

        Page <ReservDTO> reservs = reservationService.getReservations(estado,checkIn,checkOut, page);
        return ResponseEntity.ok().body(new ResponseUserDTO<>(true, "Reservas encontradas", reservs));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO<ReservDTO>> getReservation(@PathVariable String id){
        ReservDTO reservDTO= reservationService.getReservation(id);
        return ResponseEntity.ok().body(new ResponseUserDTO<>(true, "Reserva encontrada", reservDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteReservation(@PathVariable String id){
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().body(new ResponseDTO<>(true, "Reserva eliminada"));
    }
    @GetMapping("/{listingId}/host")
    public ResponseEntity<ResponseUserDTO<Page<ReservDTO>>> getListingsByReservation(@PathVariable String listingId, @RequestParam int page){
        Page<ReservDTO> reservs = reservationService.getListingsByReservation(listingId,page);
        return ResponseEntity.ok().body(new ResponseUserDTO<>(true, "Se encontraron las siguientes", reservs));
    }

}
