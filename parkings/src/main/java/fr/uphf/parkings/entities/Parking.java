package fr.uphf.parkings.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "Parking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
@Builder
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParking;
    @NotBlank
    private String nomZoneParking;

    private String AdresseParking;

    @NotBlank
    private int capaciteParking;
}
