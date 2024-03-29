package fr.uphf.parkings.services;

import fr.uphf.parkings.entities.Parking;
import fr.uphf.parkings.repositories.ParkingRepository;
import fr.uphf.parkings.services.DTO.CreateOrUpdateParkingDTO;
import fr.uphf.parkings.services.DTO.ParkingsResponseDTO;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingsService {

    private final ParkingRepository parkingRepository;

    public ParkingsService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Transactional
    public Parking createOrUpdateParking(CreateOrUpdateParkingDTO parkingDTO) {
        Parking parking = Parking.builder()
                .nomZoneParking(parkingDTO.getNomZoneParking())
                .AdresseParking(parkingDTO.getAdresseParking())
                .capaciteParking(parkingDTO.getCapaciteParking())
                .build();
        return parkingRepository.save(parking);
    }

    public Optional<Parking> getParkingById(int idParking) {
        return parkingRepository.findByIdParking(idParking);
    }

    @GetMapping
    public List<ParkingsResponseDTO> getAllParkings() {
        return parkingRepository.findAll().stream()
                .map(this::mapParkingToResponseDTO)
                .collect(Collectors.toList());
    }

    // Cette méthode est utilisée pour mapper un objet Parking vers un objet ParkingResponseDTO.
    public ParkingsResponseDTO mapParkingToResponseDTO(Parking parking) {
        return ParkingsResponseDTO.builder()
                .idParking(parking.getIdParking())
                .nomZoneParking(parking.getNomZoneParking())
                .AdresseParking(parking.getAdresseParking())
                .capaciteParking(parking.getCapaciteParking())
                .build();
    }

}
