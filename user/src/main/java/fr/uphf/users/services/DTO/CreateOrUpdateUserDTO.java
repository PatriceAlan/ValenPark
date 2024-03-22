package fr.uphf.users.services.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateOrUpdateUserDTO {

    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotBlank
    private String email;
    @NotBlank
    private String numeroTelephone;
    @NotBlank
    private String motDePasse;
}
