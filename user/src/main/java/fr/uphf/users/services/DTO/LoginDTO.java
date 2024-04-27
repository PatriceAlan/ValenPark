package fr.uphf.users.services.DTO;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    private String email;
    private String motDePasse;

}
