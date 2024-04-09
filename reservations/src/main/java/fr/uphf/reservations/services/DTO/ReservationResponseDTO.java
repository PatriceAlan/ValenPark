package fr.uphf.reservations.services.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ReservationResponseDTO {

    private Long idReservation;
    private Long idUtilisateur;
    private String immatriculationVehicule;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private int placeDeParking;
    private Long idParking;


}
