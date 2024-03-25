package fr.uphf.parkings.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Parking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParking;

    @NotBlank
    private String nomZoneParking;

    @NotBlank
    private String AdresseParking;

    @NotBlank
    private int capaciteParking;
}
