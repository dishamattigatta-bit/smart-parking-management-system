package com.parking.controller;

import com.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ParkingController {

    @Autowired
    private ParkingService service;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("parked", service.getParkedVehicles());
        model.addAttribute("allRecords", service.getAllRecords());
        model.addAttribute("available", service.getAvailableSlots());
        model.addAttribute("total", service.getTotalSlots());
        model.addAttribute("collection", service.getTotalCollection());
        return "index";
    }

    @PostMapping("/park")
    public String park(@RequestParam String vehicleNumber,
                       @RequestParam String vehicleType,
                       Model model) {
        String msg = service.parkVehicle(vehicleNumber, vehicleType);
        model.addAttribute("message", msg);
        return home(model);
    }

    @PostMapping("/exit")
    public String exit(@RequestParam String vehicleNumber, Model model) {
        String msg = service.exitVehicle(vehicleNumber);
        model.addAttribute("message", msg);
        return home(model);
    }
}
