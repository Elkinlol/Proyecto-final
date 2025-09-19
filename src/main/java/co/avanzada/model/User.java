package co.avanzada.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String state;
    private Date createAT;
    private Date dateOfBirth;
    private Rol rol;
    private Status status;
}

