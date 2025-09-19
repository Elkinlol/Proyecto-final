package co.avanzada.model;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder

public class Listing {
    private  String title;
    private  String description;
    private  String url;
    private List<String> image;
    private String id;
    private Date creationDate;
    private Adress adress;
    private Host host;
    private Float nightlyPrice;
    private Status status;
    private int maxGuest;
    private List<Services> services;
}
