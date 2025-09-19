package co.avanzada.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class Adress {
    String adress;
    String city;
    String latitud;
    String longitud;

}
