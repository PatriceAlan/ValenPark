package fr.uphf.paiements.repositories;

import fr.uphf.paiements.entities.Paiements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementsRepository extends JpaRepository<Paiements, Long> {
}
