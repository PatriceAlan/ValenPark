package fr.uphf.paiements.services.DTO;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementsDTO {

    private Long idPaiement;
    private Long idReservation;
    private int montantPaiement;
    private LocalDateTime datePaiement;

}
