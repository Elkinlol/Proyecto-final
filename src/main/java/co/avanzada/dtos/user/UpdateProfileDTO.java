package co.avanzada.dtos.user;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UpdateProfileDTO {
                 @Length(max= 100) private String fullName;
                 @Length(min = 8) private String numberPhone;
                 @Length(max = 500) private  String profilePhoto;
}
