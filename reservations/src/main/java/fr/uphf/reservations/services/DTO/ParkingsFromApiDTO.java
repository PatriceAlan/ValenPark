package fr.uphf.reservations.services.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParkingsFromApiDTO {
    private int idParking;
    private String nomZoneParking;
    private String adresseParking;
    private int capaciteParking;
}
