package fr.uphf.parkings.services.DTO;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CreateOrUpdateParkingDTO {

        @NotBlank
        private int idParking;

        @NotBlank
        private String nomZoneParking;

        @NotBlank
        private String AdresseParking;

        @NotBlank
        private int capaciteParking;

    }