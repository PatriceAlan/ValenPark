package fr.uphf.reservations.repositories;

import fr.uphf.reservations.entities.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationsRepository extends JpaRepository<Reservations, Long> {
    Optional<Reservations> findByPlaceDeParking(int parkingNumber);
}