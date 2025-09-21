package co.avanzada.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@Builder
public class Reservations {
    private Date checkIn;
    private Date checkOut;
    private int guestCount;
    private String listingId;

}
