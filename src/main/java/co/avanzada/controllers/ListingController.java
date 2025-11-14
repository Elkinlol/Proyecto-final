package co.avanzada.controllers;


import co.avanzada.dtos.extras.ResponseDTO;
import co.avanzada.dtos.extras.ResponseUserDTO;
import co.avanzada.dtos.listings.*;
import co.avanzada.model.enunms.Services;
import co.avanzada.services.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                                                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                                                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
                                                                                      @RequestParam(required = false) BigDecimal nightlyPrice,
                                                                                      @RequestParam (required = false) List<Services> servicesList, @RequestParam int page)
    {
        LocalDateTime in = checkIn != null ? checkIn.atStartOfDay() : null;
        LocalDateTime out = checkOut != null ? checkOut.atTime(23,59,59) : null;
        Page<ListingSearchResponseDTO> listings = listingService.getListingBySearch( city,  in,  out,  nightlyPrice, servicesList, page );
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

    @PostMapping(value = "/{id}/images", consumes = "multipart/form-data")
    public ResponseEntity<ResponseUserDTO<List<String>>> uploadListingImages(@PathVariable String id, @RequestParam("files") List<MultipartFile> images) throws Exception {
        List<String> urls = listingService.uploadListingImages(id, images);
        return ResponseEntity.ok(new ResponseUserDTO<>(false, "Imágenes subidas correctamente", urls));
    }

    @DeleteMapping("/{id}/images")
    public ResponseEntity<ResponseUserDTO<String>> deleteListingImage(
            @PathVariable String id,
            @RequestParam("imageId") String imageId
    ) throws Exception {
        listingService.deleteListingImage(id, imageId);
        return ResponseEntity.ok(new ResponseUserDTO<>(false, "Imagen eliminada correctamente", null));
    }

}
