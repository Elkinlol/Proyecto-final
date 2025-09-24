package co.avanzada.controllers;


import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.listings.MetricsDTO;
import co.avanzada.dtos.listings.CreateListingDTO;
import co.avanzada.dtos.listings.UpdateListingDTO;
import co.avanzada.model.Services;
import co.avanzada.services.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;



    @PostMapping
    public ResponseEntity <ResponseDTO<String>> createListing(@Valid @RequestBody CreateListingDTO createListing){
        listingService.createListing(createListing);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<List<ResponseDTO<String>>> getListingBySearch(@RequestParam(required = false) String city,
                                                            @RequestParam(required = false) String checkIn,
                                                            @RequestParam(required = false) String checkOut,
                                                            @RequestParam(required = false) Float  nightlyPrice,
                                                            @RequestParam (required = false) List<Services> servicesList)
    {
        listingService.getListingBySearch( city,  checkIn,  checkOut,  nightlyPrice, servicesList );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/metrics")
    public ResponseEntity<ResponseDTO<MetricsDTO>> getMetrics(@PathVariable String id, @RequestParam(required = false) String startDate,
                                                 @RequestParam (required = false) String endDate){
        //MetricsDTO metricts = listingService.getMetrics(id, startDate, endDate);
        return ResponseEntity.ok().build();
    }

}
