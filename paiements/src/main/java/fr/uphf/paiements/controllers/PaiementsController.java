package fr.uphf.paiements.controllers;

import fr.uphf.paiements.entities.Paiements;
import fr.uphf.paiements.repositories.PaiementsRepository;
import fr.uphf.paiements.services.DTO.PaiementsDTO;
import fr.uphf.paiements.services.PaiementsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paiements")
@CrossOrigin(origins = "http://localhost:5173")
public class PaiementsController {

    private final PaiementsService paiementsService;
    private final PaiementsRepository paiementsRepository;

    public PaiementsController(PaiementsService paiementsService, PaiementsRepository paiementsRepository) {
        this.paiementsService = paiementsService;
        this.paiementsRepository = paiementsRepository;
    }

    @PostMapping
    public ResponseEntity<PaiementsDTO> createPaiement(@RequestBody PaiementsDTO paiementDTO) {
        PaiementsDTO createdPaiement = mapPaiementToResponseDto(paiementsService.createPaiement(paiementDTO));
        return ResponseEntity.ok(createdPaiement);

    }

    @PutMapping("/{idPaiement}")
    public ResponseEntity<PaiementsDTO> updatePaiement(@PathVariable Long idPaiement, @RequestBody PaiementsDTO paiementDTO) {
        PaiementsDTO updatedPaiement = mapPaiementToResponseDto(paiementsService.updatePaiement(idPaiement, paiementDTO));
        return ResponseEntity.ok(updatedPaiement);
    }

    @DeleteMapping("/{idPaiement}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long idPaiement) {
        paiementsService.deletePaiement(idPaiement);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PaiementsDTO>> getAllPaiements() {
        List<PaiementsDTO> paiements = paiementsService.getAllPaiements();
        return ResponseEntity.ok(paiements);
    }

    private PaiementsDTO mapPaiementToResponseDto(Paiements paiement) {
        return PaiementsDTO.builder()
                .idPaiement(paiement.getIdPaiement())
                .idReservation(paiement.getIdReservation())
                .montantPaiement(paiement.getMontantPaiement())
                .datePaiement(paiement.getDatePaiement())
                .build();
    }

    @GetMapping("/{idPaiement}")
    public Optional<Paiements> getPaiementById(@PathVariable Long idPaiement) {
        return paiementsRepository.findById(idPaiement);
    }
}
