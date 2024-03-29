package fr.uphf.parkings.services.DTO;


import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ParkingResponseDTO {
    private int idParking;
    private String nomZoneParking;
    private String AdresseParking;
    private int capaciteParking;
}





