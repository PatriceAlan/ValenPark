package fr.uphf.parkings.services;

import fr.uphf.parkings.entities.Parking;
import fr.uphf.parkings.repositories.ParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {
    private static final Logger logger = LoggerFactory.getLogger(ParkingService.class);
    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }
    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    public Optional<Parking> getParkingById(int id) {
        return parkingRepository.findById(id);
    }

    public Parking saveParking(Parking parking) {
        logger.info("Saving parking: {}", parking);
        return parkingRepository.save(parking);
    }

    public void deleteParking(int id) {
        parkingRepository.deleteById(id);
    }
}
