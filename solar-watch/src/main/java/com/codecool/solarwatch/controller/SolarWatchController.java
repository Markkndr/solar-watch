package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.dto.SolarWatchResponse;
import com.codecool.solarwatch.service.SolarWatchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/solar")
public class SolarWatchController {

    private final SolarWatchService solarWatchService;

    public SolarWatchController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping
    public ResponseEntity<SolarWatchResponse> getSolar(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String date) {

        if (city == null || date == null) {
            return ResponseEntity.badRequest().body(null);
        }

        SolarWatchResponse response = solarWatchService.getSolarData(city, date);
        return ResponseEntity.ok(response);
    }
}
