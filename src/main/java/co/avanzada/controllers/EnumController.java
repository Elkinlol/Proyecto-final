package co.avanzada.controllers;


import co.avanzada.model.enunms.ReservationStatus;
import co.avanzada.model.enunms.Services;
import co.avanzada.model.enunms.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping
    public ResponseEntity<List<String>> getServices() {
        List<String> services = Arrays.stream(Services.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(services);
    }

    @GetMapping("/status")
    public ResponseEntity<List<String>> getReservationStatus() {
        List<String> reservationStatuses = Arrays.stream(ReservationStatus.values())
                .map(Enum::name).collect(Collectors.toList());
        return ResponseEntity.ok(reservationStatuses);

    }



}
