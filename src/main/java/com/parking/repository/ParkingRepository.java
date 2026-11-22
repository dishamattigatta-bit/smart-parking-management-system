package com.parking.repository;

import com.parking.model.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingRecord, Long> {

    // SQL: SELECT * FROM parking_records WHERE status='PARKED'
    List<ParkingRecord> findByStatus(String status);

    // SQL: SELECT * FROM parking_records WHERE vehicle_number=? AND status='PARKED'
    Optional<ParkingRecord> findByVehicleNumberAndStatus(String vehicleNumber, String status);

    // SQL: SELECT * FROM parking_records WHERE status='PARKED' ORDER BY slot_number
    List<ParkingRecord> findByStatusOrderBySlotNumberAsc(String status);
}
