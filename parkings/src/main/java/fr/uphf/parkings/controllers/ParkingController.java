package fr.uphf.parkings.controllers;
import fr.uphf.parkings.entities.Parking;
import fr.uphf.parkings.repositories.ParkingRepository;
import fr.uphf.parkings.services.DTO.CreateOrUpdateParkingDTO;
import fr.uphf.parkings.services.DTO.ParkingsResponseDTO;
import fr.uphf.parkings.services.ParkingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parkings")
public class ParkingController {

    private final ParkingsService parkingsService;
    private final ParkingRepository parkingRepository;


    public ParkingController(ParkingsService parkingsService, ParkingRepository parkingRepository) {
        this.parkingsService = parkingsService;
        this.parkingRepository = parkingRepository;
    }


    @PostMapping
    public ResponseEntity<ParkingsResponseDTO> createParking(@RequestBody CreateOrUpdateParkingDTO parkingDTO) {
        ParkingsResponseDTO createdParking = mapParkingToResponseDTO(parkingsService.createParking(parkingDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParking);
    }

    @GetMapping("/{idParking}")
    public ResponseEntity<ParkingsResponseDTO> getParkingById(@PathVariable Long idParking) {
        Optional<Parking> parkingOptional = parkingRepository.findById(idParking);
        if (parkingOptional.isPresent()) {
            Parking parking = parkingOptional.get();
            ParkingsResponseDTO parkingsResponseDTO = mapParkingToResponseDTO(parking);
            return ResponseEntity.ok(parkingsResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ParkingsResponseDTO>> getAllParkings() {
        List<ParkingsResponseDTO> parkings = parkingsService.getAllParkings();
        return ResponseEntity.ok(parkings);
    }

    @PutMapping("/{idParking}")
    public ResponseEntity<ParkingsResponseDTO> updateParking(@PathVariable Long idParking, @RequestBody CreateOrUpdateParkingDTO parkingDTO) {
        ParkingsResponseDTO updatedParking = mapParkingToResponseDTO(parkingsService.updateParking(idParking, parkingDTO));
        return ResponseEntity.ok(updatedParking);
    }

    @DeleteMapping("/{idParking}")
    public ResponseEntity<Void> deleteParkingById(@PathVariable Long idParking) {
        parkingsService.deleteParking(idParking);
        return ResponseEntity.noContent().build();
    }


    private ParkingsResponseDTO mapParkingToResponseDTO(Parking parking) {
        return ParkingsResponseDTO.builder()
                .idParking(parking.getIdParking())
                .nomZoneParking(parking.getNomZoneParking())
                .AdresseParking(parking.getAdresseParking())
                .capaciteParking(parking.getCapaciteParking())
                .tarifHoraire(parking.getTarifHoraire())
                .build();
    }

}