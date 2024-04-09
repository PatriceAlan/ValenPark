package fr.uphf.parkings.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParking;

    @NotBlank
    private String nomZoneParking;

    @NotBlank
    private String adresseParking;

    @NotNull
    private int capaciteParking;

    @NotNull
    private double tarifHoraire;
}
