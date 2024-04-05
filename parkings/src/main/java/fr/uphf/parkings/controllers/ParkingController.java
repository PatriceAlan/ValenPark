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
public class ParkingController {

    private final ParkingsService parkingsService;
    private final ParkingRepository parkingRepository;


    public ParkingController(ParkingsService parkingsService, ParkingRepository parkingRepository) {
        this.parkingsService = parkingsService;
        this.parkingRepository = parkingRepository;
    }


    @PostMapping
    public ResponseEntity<ParkingsResponseDTO> createParking(@RequestBody CreateOrUpdateParkingDTO parkingDTO) {
        ParkingsResponseDTO createdParking = mapParkingToResponseDTO(parkingsService.createOrUpdateParking(parkingDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParking);
    }

    @GetMapping("/{idParking}")
    public ResponseEntity<ParkingsResponseDTO> getParkingById(@PathVariable int idParking) {
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


    private ParkingsResponseDTO mapParkingToResponseDTO(Parking parking) {
        return ParkingsResponseDTO.builder()
                .idParking(parking.getIdParking())
                .nomZoneParking(parking.getNomZoneParking())
                .AdresseParking(parking.getAdresseParking())
                .capaciteParking(parking.getCapaciteParking())
                .build();
    }

}