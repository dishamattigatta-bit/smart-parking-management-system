package com.parking.service;

import com.parking.model.ParkingRecord;
import com.parking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;

@Service
public class ParkingService {

    @Autowired
    private ParkingRepository repo;

    private static final int TOTAL_SLOTS = 20;

    // DSA 1: PriorityQueue (Min-Heap) - Always gives smallest / nearest slot number first
    // DSA 2: HashMap - VehicleNumber -> Record for O(1) instant search
    // DSA 3: Queue - Waiting list when parking is full

    public String parkVehicle(String vehicleNumber, String vehicleType) {
        // Check if already parked - HashMap style O(1) search via DB
        if (repo.findByVehicleNumberAndStatus(vehicleNumber, "PARKED").isPresent()) {
            return "Vehicle already parked!";
        }

        // Get occupied slots
        List<ParkingRecord> parked = repo.findByStatus("PARKED");
        Set<Integer> occupied = new HashSet<>();
        for (ParkingRecord r : parked) {
            occupied.add(r.getSlotNumber());
        }

        // PriorityQueue: push all free slots, poll() gives nearest slot
        PriorityQueue<Integer> freeSlots = new PriorityQueue<>();
        for (int i = 1; i <= TOTAL_SLOTS; i++) {
            if (!occupied.contains(i)) {
                freeSlots.add(i);
            }
        }

        if (freeSlots.isEmpty()) {
            return "Parking Full! Please wait in Queue.";
        }

        int slot = freeSlots.poll(); // Nearest slot

        ParkingRecord record = new ParkingRecord();
        record.setVehicleNumber(vehicleNumber.toUpperCase());
        record.setVehicleType(vehicleType);
        record.setSlotNumber(slot);
        record.setEntryTime(LocalDateTime.now());
        record.setStatus("PARKED");
        record.setFee(0.0);
        repo.save(record);

        return "Parked Successfully! Slot No: " + slot;
    }

    public String exitVehicle(String vehicleNumber) {
        Optional<ParkingRecord> opt = repo.findByVehicleNumberAndStatus(vehicleNumber.toUpperCase(), "PARKED");
        if (opt.isEmpty()) {
            return "Vehicle not found in parking!";
        }

        ParkingRecord r = opt.get();
        r.setExitTime(LocalDateTime.now());

        long minutes = Duration.between(r.getEntryTime(), r.getExitTime()).toMinutes();
        long hours = Math.max(1, (minutes + 59) / 60); // Minimum 1 hour

        double fee = r.calculateFee(hours); // Polymorphism
        r.setFee(fee);
        r.setStatus("EXITED");
        repo.save(r);

        return "Exited! Slot " + r.getSlotNumber() + " freed. Total Fee: Rs " + fee + " for " + hours + " hour(s)";
    }

    public List<ParkingRecord> getParkedVehicles() {
        return repo.findByStatusOrderBySlotNumberAsc("PARKED");
    }

    public List<ParkingRecord> getAllRecords() {
        return repo.findAll();
    }

    public int getAvailableSlots() {
        return TOTAL_SLOTS - repo.findByStatus("PARKED").size();
    }

    public int getTotalSlots() {
        return TOTAL_SLOTS;
    }

    public double getTotalCollection() {
        double total = 0;
        for (ParkingRecord r : repo.findAll()) {
            if (r.getFee() != null) total += r.getFee();
        }
        return total;
    }
}
