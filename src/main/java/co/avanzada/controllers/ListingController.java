package co.avanzada.controllers;


import co.avanzada.dtos.CreateListingDTO;
import co.avanzada.dtos.UpdateListingDTO;
import co.avanzada.model.Listings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Listings")
public class ListingController {

    @PostMapping
    public ResponseEntity <String> createListing(@RequestBody CreateListingDTO CreateListing){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity <String>  updateListing(@PathVariable String id, @RequestBody UpdateListingDTO UpdateListing){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <String> deleteListing(@PathVariable String id){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getListing(@PathVariable String id){
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/search")
    public ResponseEntity<String> getListingBySearch(@RequestParam String search){
        return ResponseEntity.ok().build();
    }//PREGUNTAR

    @GetMapping("/{id}/metrics")
    public ResponseEntity<String> getMetrics(@PathVariable String id){
        return ResponseEntity.ok().build();
    }

}
