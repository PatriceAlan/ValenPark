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
@RequestMapping("/api/reservations")
public class ReservationsController {

    private final ReservationsService reservationsService;
    private final ReservationsRepository reservationsRepository;

    public ReservationsController(ReservationsService reservationsService, ReservationsRepository reservationsRepository) {
        this.reservationsService = reservationsService;
        this.reservationsRepository = reservationsRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody CreateOrUpdateReservationDTO reservationDTO) {
        ReservationResponseDTO createdReservation = mapReservationToResponseDto(reservationsService.createOrUpdateReservation(reservationDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
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
