package fr.uphf.reservations.services.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CreateOrUpdateReservationDTO {

    @NotBlank
    private Long idUtilisateur;
    @NotBlank
    private LocalDateTime dateDebut;
    @NotBlank
    private LocalDateTime dateFin;
    @NotBlank
    private int placeDeParking;

}
