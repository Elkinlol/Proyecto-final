package co.avanzada.controllers;


import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.extras.ResponseUserDTO;
import co.avanzada.dtos.listings.*;
import co.avanzada.model.enunms.Services;
import co.avanzada.services.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;



    @PostMapping
    public ResponseEntity <ResponseUserDTO<ListingDTO>> createListing(@Valid @RequestBody CreateListingDTO createListing){
        ListingDTO listingDto = listingService.createListing(createListing);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUserDTO(true, "Alojamiento creado correctamente", listingDto ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity <ResponseUserDTO<ListingDTO>>  updateListing(@PathVariable String id, @Valid @RequestBody UpdateListingDTO UpdateListing){
        ListingDTO listingDTO= listingService.updateListing(id, UpdateListing);
        return ResponseEntity.ok().body(new ResponseUserDTO<>(true, "Alojamiento actualizado correctamente", listingDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <ResponseDTO<String>> deleteListing(@PathVariable String id){
        listingService.deleteListing(id);
        return ResponseEntity.ok().body(new ResponseDTO<>(true , "Alojamiento eliminado correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO<ListingDTO>> getListing(@PathVariable String id){
        ListingDTO listingDTO = listingService.getListing(id);
        return ResponseEntity.ok().body(new ResponseUserDTO<>(true, "Alojamiento encontrado", listingDTO));
    }

    @GetMapping ("/search")
    public ResponseEntity<ResponseUserDTO<Page<ListingSearchResponseDTO>>> getListingBySearch(@RequestParam(required = false) String city,
                                                                                      @RequestParam(required = false) LocalDate checkIn,
                                                                                      @RequestParam(required = false) LocalDate checkOut,
                                                                                      @RequestParam(required = false) BigDecimal nightlyPrice,
                                                                                      @RequestParam (required = false) List<Services> servicesList, @RequestParam int page)
    {
        Page<ListingSearchResponseDTO> listings = listingService.getListingBySearch( city,  checkIn,  checkOut,  nightlyPrice, servicesList, page );
        return ResponseEntity.ok().body(new ResponseUserDTO(true, "Se encontraron las siguiente", listings));
    }

    @GetMapping("/{id}/metrics")
    public ResponseEntity<ResponseUserDTO<MetricsDTO>> getMetrics(@PathVariable String id, @RequestParam(required = false) LocalDate startDate,
                                                 @RequestParam (required = false) LocalDate endDate){
        MetricsDTO metricts = listingService.getMetrics(id, startDate, endDate);
        return ResponseEntity.ok().body(new ResponseUserDTO<>(true, "Se encuentras las siguentes metricas", metricts));
    }

    @GetMapping
    public ResponseEntity<ResponseUserDTO<Page<ListingDTO>>> getListings(@RequestParam int page){
        Page<ListingDTO> page1 = listingService.getListingFromHost(page);
        return ResponseEntity.ok().body(new ResponseUserDTO<>(true, "Alojamientos encontrados", page1));

    }

}
