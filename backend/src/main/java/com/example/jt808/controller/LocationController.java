package com.example.jt808.controller;

import com.example.jt808.repository.LocationRecordRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    private final LocationRecordRepository locationRecordRepository;

    public LocationController(LocationRecordRepository locationRecordRepository) {
        this.locationRecordRepository = locationRecordRepository;
    }

    @GetMapping("/latest")
    public Object latest() {
        return locationRecordRepository.findTop50ByOrderByCreatedAtDesc();
    }
}
