package co.avanzada.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Host {

    @Column(nullable = false, length = 500)
    private  String aboutMe;

    @Column(nullable = false, length = 500)
    private  String documents;

}
