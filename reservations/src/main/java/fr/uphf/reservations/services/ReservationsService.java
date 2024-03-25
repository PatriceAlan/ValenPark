package fr.uphf.reservations.services;

import fr.uphf.reservations.entities.Reservations;
import fr.uphf.reservations.repositories.ReservationsRepository;
import fr.uphf.reservations.services.DTO.CreateOrUpdateReservationDTO;
import fr.uphf.reservations.services.DTO.ReservationResponseDTO;
import fr.uphf.reservations.services.DTO.UtilisateursFromApiDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationsService {

    @Autowired
    private WebClient.Builder webClient;

    private final ReservationsRepository reservationsRepository;

    public ReservationsService(ReservationsRepository reservationsRepository) {
        this.reservationsRepository = reservationsRepository;
    }

    @Transactional
    public Reservations createOrUpdateReservation(CreateOrUpdateReservationDTO reservationDTO) {
        UtilisateursFromApiDTO utilisateursFromApiDTO = webClient.baseUrl("http://users/")
                .build()
                .get()
                .uri("/api/users/" + reservationDTO.getIdUtilisateur())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UtilisateursFromApiDTO.class)
                .block();
        if (utilisateursFromApiDTO == null) {
            throw new RuntimeException("User not found");
        }
        Reservations reservation = Reservations.builder()
                .idUtilisateur(utilisateursFromApiDTO.getId())
                .dateDebut(reservationDTO.getDateDebut())
                .dateFin(reservationDTO.getDateFin())
                .placeDeParking(reservationDTO.getPlaceDeParking())
                .build();
        return reservationsRepository.save(reservation);
    }

    public List<ReservationResponseDTO> getAllReservations() {
        return reservationsRepository.findAll().stream()
                .map(this::mapReservationToResponseDto)
                .collect(Collectors.toList());
    }

    // Cette méthode est utilisée pour mapper un objet Reservation vers un objet ReservationResponseDTO.
    public ReservationResponseDTO mapReservationToResponseDto(Reservations reservation) {
        return ReservationResponseDTO.builder()
                .id(reservation.getId())
                .idUtilisateur(reservation.getIdUtilisateur())
                .dateDebut(reservation.getDateDebut())
                .dateFin(reservation.getDateFin())
                .placeDeParking(reservation.getPlaceDeParking())
                .build();
    }
}
