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
                .uri("/api/parkings/" + reservationDTO.getPlaceDeParking())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ParkingsFromApiDTO.class)
                .block();

        if (utilisateursFromApiDTO == null || parkingsFromApiDTO == null) {
            throw new IllegalArgumentException("Utilisateur ou parking introuvable");
        }

        // Génération aléatoire du numéro de place de parking et vérification de son unicité
        int parkingCapacity = parkingsFromApiDTO.getCapaciteParking();
        int randomParkingNumber = generateUniqueRandomParkingNumber(parkingCapacity);

        Reservations reservation = Reservations.builder()
                .idUtilisateur(utilisateursFromApiDTO.getId())
                .dateDebut(reservationDTO.getDateDebut())
                .dateFin(reservationDTO.getDateFin())
                .placeDeParking(randomParkingNumber)
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
                .id(reservation.getIdReservation())
                .idUtilisateur(reservation.getIdUtilisateur())
                .dateDebut(reservation.getDateDebut())
                .dateFin(reservation.getDateFin())
                .placeDeParking(reservation.getPlaceDeParking())
                .build();
    }

    // Méthode pour générer un numéro de place de parking aléatoire unique
    private int generateUniqueRandomParkingNumber(int capacity) {
        Random random = new Random();
        int randomParkingNumber;
        do {
            randomParkingNumber = random.nextInt(capacity) + 1;
        } while (isParkingNumberTaken(randomParkingNumber));
        return randomParkingNumber;
    }

    // Méthode pour vérifier si un numéro de place de parking est déjà pris
    private boolean isParkingNumberTaken(int parkingNumber) {
        Optional<Reservations> existingReservation = reservationsRepository.findByPlaceDeParking(parkingNumber);
        return existingReservation.isPresent();
    }
}
