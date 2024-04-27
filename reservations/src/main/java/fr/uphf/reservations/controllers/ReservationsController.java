package fr.uphf.reservations.controllers;

import fr.uphf.reservations.entities.Reservations;
import fr.uphf.reservations.repositories.ReservationsRepository;
import fr.uphf.reservations.services.DTO.CreateOrUpdateReservationDTO;
import fr.uphf.reservations.services.DTO.ReservationResponseDTO;
import fr.uphf.reservations.services.ReservationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationsController {

    private final ReservationsService reservationsService;
    private final ReservationsRepository reservationsRepository;

    public ReservationsController(ReservationsService reservationsService, ReservationsRepository reservationsRepository) {
        this.reservationsService = reservationsService;
        this.reservationsRepository = reservationsRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody CreateOrUpdateReservationDTO reservationDTO) {
        ReservationResponseDTO createdReservation = mapReservationToResponseDto(reservationsService.createReservation(reservationDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @PutMapping("/{idReservation}")
    public ResponseEntity<ReservationResponseDTO> updateReservation(@PathVariable Long idReservation, @RequestBody CreateOrUpdateReservationDTO reservationDTO) {
        ReservationResponseDTO updatedReservation = mapReservationToResponseDto(reservationsService.updateReservation(idReservation, reservationDTO));
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{idReservation}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long idReservation) {
        reservationsService.deleteReservation(idReservation);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        List<ReservationResponseDTO> reservations = reservationsService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // Cette méthode est utilisée pour mapper un objet Reservation vers un objet ReservationResponseDTO.
    private ReservationResponseDTO mapReservationToResponseDto(Reservations reservation) {
        return ReservationResponseDTO.builder()
                .idReservation(reservation.getIdReservation())
                .idUtilisateur(reservation.getIdUtilisateur())
                .immatriculationVehicule(reservation.getImmatriculationVehicule())
                .dateDebut(reservation.getDateDebut())
                .dateFin(reservation.getDateFin())
                .placeDeParking(reservation.getPlaceDeParking())
                .idParking(reservation.getIdParking())
                .build();
    }

    @GetMapping("/{idReservation}")
    public Optional<Reservations> getReservationById(@PathVariable  Long idReservation) {
        return reservationsRepository.findById(idReservation);
    }
}
