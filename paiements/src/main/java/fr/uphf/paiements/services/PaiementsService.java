package fr.uphf.paiements.services;

import fr.uphf.paiements.entities.Paiements;
import fr.uphf.paiements.repositories.PaiementsRepository;
import fr.uphf.paiements.services.DTO.PaiementsDTO;
import fr.uphf.paiements.services.DTO.ReservationsFromApiDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaiementsService {

    private final WebClient.Builder webClient;

    private final RabbitTemplate rabbitTemplate;

    private final PaiementsRepository paiementsRepository;

    public PaiementsService(WebClient.Builder webClient, RabbitTemplate rabbitTemplate, PaiementsRepository paiementsRepository) {
        this.webClient = webClient;
        this.rabbitTemplate = rabbitTemplate;
        this.paiementsRepository = paiementsRepository;
    }

    @RabbitListener(queues = "reservation_queue")
    public void receiveReservationCreationMessage(Long idReservation) {
        System.out.println("Confirmation pour la réservation " + idReservation);
    }

    public Paiements createOrUpdatePaiement(PaiementsDTO paiementsDTO) {
        ReservationsFromApiDTO reservationsFromApiDTO = webClient.baseUrl("http://reservations/")
                .build()
                .get()
                .uri("/reservations/" + paiementsDTO.getIdReservation())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ReservationsFromApiDTO.class)
                .block();

        if (reservationsFromApiDTO == null) {
            throw new IllegalArgumentException("Reservation introuvable");
        }

        Paiements paiements = Paiements.builder()
                .idReservation(paiementsDTO.getIdReservation())
                .montantPaiement(paiementsDTO.getMontantPaiement())
                .datePaiement(paiementsDTO.getDatePaiement())
                .build();

        Paiements paiementsSaved = paiementsRepository.save(paiements);

        rabbitTemplate.convertAndSend("Paiement réussi","paiement_queue", paiementsSaved.getIdPaiement());
        return paiementsSaved;
    }

    public List<PaiementsDTO> getAllPaiements() {
        return paiementsRepository.findAll().stream()
                .map(this::mapPaiementsToPaiementsDTO)
                .collect(Collectors.toList());
    }

    // Cette méthode permet de convertir un objet Paiements en un objet PaiementsDTO
    public PaiementsDTO mapPaiementsToPaiementsDTO(Paiements paiements) {
        return PaiementsDTO.builder()
                .idPaiement(paiements.getIdPaiement())
                .idReservation(paiements.getIdReservation())
                .montantPaiement(paiements.getMontantPaiement())
                .datePaiement(paiements.getDatePaiement())
                .build();
    }
}
