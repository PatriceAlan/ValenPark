package fr.uphf.paiements.services.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ReservationsFromApiDTO {

    private Long idReservation;
    private Long idUtilisateur;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private int placeDeParking;
    private int idParking;


}
