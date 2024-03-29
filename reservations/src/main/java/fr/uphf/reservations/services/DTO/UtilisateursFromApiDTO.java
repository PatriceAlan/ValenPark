package fr.uphf.reservations.services.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UtilisateursFromApiDTO {
    private Long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String numeroTelephone;
}
