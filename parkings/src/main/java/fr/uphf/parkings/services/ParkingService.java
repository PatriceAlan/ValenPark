package fr.uphf.parkings.services;

import fr.uphf.parkings.entities.Parking;
import fr.uphf.parkings.repositories.ParkingRepository;
import fr.uphf.parkings.services.DTO.CreateOrUpdateParkingDTO;
import fr.uphf.parkings.services.DTO.ParkingResponseDTO;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
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

    public List<ParkingResponseDTO> getAllParking() {
        return parkingRepository.findAll().stream()
                .map(this::mapParkingToResponseDTO)
                .collect(Collectors.toList());
    }

    public ParkingResponseDTO mapParkingToResponseDTO(Parking parking) {
        return ParkingResponseDTO.builder()
                .idParking(parking.getIdParking())
                .nomZoneParking(parking.getNomZoneParking())
                .AdresseParking(parking.getAdresseParking())
                .capaciteParking(parking.getCapaciteParking())
                .build();
    }

    public List<ParkingResponseDTO> getAllParking() {
        return null;
    }
}
