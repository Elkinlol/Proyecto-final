package co.avanzada.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class Auth {
    String email;
    String password;
}
