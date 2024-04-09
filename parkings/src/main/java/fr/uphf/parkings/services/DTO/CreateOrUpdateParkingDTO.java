package fr.uphf.parkings.services.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CreateOrUpdateParkingDTO {

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