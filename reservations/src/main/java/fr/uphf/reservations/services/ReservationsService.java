package fr.uphf.reservations.services;

import fr.uphf.reservations.entities.Reservations;
import fr.uphf.reservations.repositories.ReservationsRepository;
import fr.uphf.reservations.services.DTO.CreateOrUpdateReservationDTO;
import fr.uphf.reservations.services.DTO.ParkingsFromApiDTO;
import fr.uphf.reservations.services.DTO.ReservationResponseDTO;
import fr.uphf.reservations.services.DTO.UtilisateursFromApiDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.Random;
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

        ParkingsFromApiDTO parkingsFromApiDTO = webClient.baseUrl("http://parkings/")
                .build()
                .get()
                .uri("/api/parkings/" + reservationDTO.getIdParking())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ParkingsFromApiDTO.class)
                .block();

        if (utilisateursFromApiDTO == null || parkingsFromApiDTO == null) {
            throw new IllegalArgumentException("Utilisateur ou parking introuvable");
        }

        int capaciteParking = parkingsFromApiDTO.getCapaciteParking();
        int numeroPlaceDeParking = reservationDTO.getPlaceDeParking();

        if (isParkingNumberTaken(numeroPlaceDeParking)) {
            throw new IllegalArgumentException("Numéro de place de parking déjà pris");
        }
        if (isParkingFull(capaciteParking)) {
            throw new IllegalArgumentException("Parking plein");
        }
        Reservations reservation = Reservations.builder()
                .idUtilisateur(utilisateursFromApiDTO.getIdUtilisateur())
                .dateDebut(reservationDTO.getDateDebut())
                .dateFin(reservationDTO.getDateFin())
                .placeDeParking(numeroPlaceDeParking)
                .idParking(reservationDTO.getIdParking())
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
                .idReservation(reservation.getIdReservation())
                .idUtilisateur(reservation.getIdUtilisateur())
                .dateDebut(reservation.getDateDebut())
                .dateFin(reservation.getDateFin())
                .placeDeParking(reservation.getPlaceDeParking())
                .idParking(reservation.getIdParking())
                .build();
    }


    // Méthode pour vérifier si un numéro de place de parking est déjà pris
    private boolean isParkingNumberTaken(int parkingNumber) {
        Optional<Reservations> existingReservation = reservationsRepository.findByPlaceDeParking(parkingNumber);
        return existingReservation.isPresent();
    }

    // Méthode pour verifier si le parking est plein
    private boolean isParkingFull(int parkingCapacity) {
        List<Reservations> reservations = reservationsRepository.findAll();
        return reservations.size() >= parkingCapacity;
    }
}
