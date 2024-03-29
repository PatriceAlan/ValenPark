package fr.uphf.parkings.controllers;
import fr.uphf.parkings.entities.Parking;
import fr.uphf.parkings.repositories.ParkingRepository;
import fr.uphf.parkings.services.DTO.CreateOrUpdateParkingDTO;
import fr.uphf.parkings.services.DTO.ParkingResponseDTO;
import fr.uphf.parkings.services.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingRepository parkingRepository;
    public interface ParkingService {
        List<ParkingResponseDTO> getAllParking();

        Parking createOrUpdateParking(CreateOrUpdateParkingDTO parkingDTO);
    }

    public ParkingController(ParkingService parkingService, ParkingRepository parkingRepository) {
        this.parkingService = parkingService;
        this.parkingRepository = parkingRepository;
    }


    @PostMapping
    public ResponseEntity<ParkingResponseDTO> createParking(@RequestBody CreateOrUpdateParkingDTO parkingDTO) {
        ParkingResponseDTO createdParking = mapParkingToResponseDTO(parkingService.createOrUpdateParking(parkingDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParking);
    }

    private ParkingResponseDTO mapParkingToResponseDTO(Parking orUpdateParking) {
        return null;
    }

    @GetMapping("/{idParking}")
    public ResponseEntity<ParkingResponseDTO> getParkingById(@PathVariable int idParking) {
        Optional<Parking> parkingOptional = parkingRepository.findById(idParking);
        if (parkingOptional.isPresent()) {
            Parking parking = parkingOptional.get();
           ParkingResponseDTO parkingResponseDTO = mapParkingToResponseDTO(parking);
            return ResponseEntity.ok(parkingResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ParkingResponseDTO>> getAllParking() {
        List<ParkingResponseDTO> parking = parkingService.getAllParking();
        return ResponseEntity.ok(parking);
    }


    private ParkingResponseDTO mapParkingToResponseDto(Parking parking) {
        return ParkingResponseDTO.builder()
                .idParking(parking.getIdParking())
                .nomZoneParking(parking.getNomZoneParking())
                .AdresseParking(parking.getAdresseParking())
                .capaciteParking(parking.getCapaciteParking())
                .build();
    }

}