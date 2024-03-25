package fr.uphf.reservations.repositories;

import fr.uphf.reservations.entities.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationsRepository extends JpaRepository<Reservations, Long> {
}