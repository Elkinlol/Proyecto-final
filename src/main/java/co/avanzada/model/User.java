package co.avanzada.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private String state;
    private LocalDateTime createAT;
    private LocalDateTime dateOfBirth;
    private Rol rol;
    private Status status;
}

