package co.avanzada.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Host {
    private  String aboutMe;
    private  String documents;
    private User user;
}
