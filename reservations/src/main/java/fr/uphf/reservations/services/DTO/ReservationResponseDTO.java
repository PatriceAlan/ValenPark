package fr.uphf.reservations.services.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ReservationResponseDTO {

    private Long id;
    private Long idUtilisateur;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private int placeDeParking;


}
