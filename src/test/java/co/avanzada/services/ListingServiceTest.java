package co.avanzada.services;

import co.avanzada.dtos.listings.AddressDTO;
import co.avanzada.dtos.listings.CreateListingDTO;
import co.avanzada.dtos.listings.UpdateListingDTO;
import co.avanzada.model.Listing;
import co.avanzada.model.enunms.Services;
import co.avanzada.services.impl.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListingServiceTest{

    @Autowired
    private ListingService listingService;


    @Mock
    private JavaMailSender javaMailSender;


    private UpdateListingDTO updateListingDTO;



    // ==========================
    // Test actualizar listing exitoso
    // ==========================
    @Test
    @Sql("classpath:dataset.sql")
    void testUpdateListing() {
        UpdateListingDTO dto = new UpdateListingDTO(
                "Nuevo título",
                "Nueva descripción",
                new BigDecimal("120.0"),
                4
        );

        assertDoesNotThrow(() -> listingService.updateListing("listing1", dto));
    }
}
