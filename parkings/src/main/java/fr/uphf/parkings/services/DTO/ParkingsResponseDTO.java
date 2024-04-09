package fr.uphf.parkings.services.DTO;


import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ParkingsResponseDTO {
    private Long idParking;
    private String nomZoneParking;
    private String AdresseParking;
    private int capaciteParking;
}





