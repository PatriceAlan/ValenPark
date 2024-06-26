package fr.uphf.users.services.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDTO {
    private Long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String numeroTelephone;

}
