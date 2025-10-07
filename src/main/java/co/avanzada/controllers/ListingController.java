package co.avanzada.controllers;


import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.extras.ResponseUserDTO;
import co.avanzada.dtos.listings.ListingDTO;
import co.avanzada.dtos.listings.MetricsDTO;
import co.avanzada.dtos.listings.CreateListingDTO;
import co.avanzada.dtos.listings.UpdateListingDTO;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseUserDTO(false, "Alojamiento creado correctamente", listingDto ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity <ResponseDTO<String>>  updateListing(@PathVariable String id, @Valid @RequestBody UpdateListingDTO UpdateListing){
        listingService.updateListing(id, UpdateListing);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <ResponseDTO<String>> deleteListing(@PathVariable String id){
        listingService.deleteListing(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> getListing(@PathVariable String id){
        listingService.getListing(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/search")
    public ResponseEntity<ResponseUserDTO<Page<ListingDTO>>> getListingBySearch(@RequestParam(required = false) String city,
                                                                                      @RequestParam(required = false) LocalDate checkIn,
                                                                                      @RequestParam(required = false) LocalDate checkOut,
                                                                                      @RequestParam(required = false) BigDecimal nightlyPrice,
                                                                                      @RequestParam (required = false) List<Services> servicesList, @RequestParam int page)
    {
        Page<ListingDTO> listings = listingService.getListingBySearch( city,  checkIn,  checkOut,  nightlyPrice, servicesList, page );
        return ResponseEntity.ok().body(new ResponseUserDTO(false, "Se encontraron las siguiente", listings));
    }

    @GetMapping("/{id}/metrics")
    public ResponseEntity<ResponseDTO<MetricsDTO>> getMetrics(@PathVariable String id, @RequestParam(required = false) String startDate,
                                                 @RequestParam (required = false) String endDate){
        //MetricsDTO metricts = listingService.getMetrics(id, startDate, endDate);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ListingDTO>>> getListings(@RequestParam int page){
        listingService.getListingFromHost(page);
        return ResponseEntity.ok().build();

    }

}
