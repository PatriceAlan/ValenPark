package fr.uphf.parkings.repositories;

import fr.uphf.parkings.entities.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking,Integer> {
    Optional<Parking> findById(int idParking);
}

