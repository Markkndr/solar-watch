package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.dto.HistoryItemResponse;
import com.codecool.solarwatch.dto.SolarResponse;
import com.codecool.solarwatch.service.SolarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/solar")
public class SolarController {
    private final SolarService solarService;

    public SolarController(SolarService solarService) {
        this.solarService = solarService;
    }

    @GetMapping
    public SolarResponse getSolarData(@RequestParam String city,
                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        return solarService.getSolarData(city, date, userDetails.getUsername());
    }

    @GetMapping("/history")
    public List<HistoryItemResponse> getHistory(@AuthenticationPrincipal UserDetails userDetails) {
        return solarService.getHistory(userDetails.getUsername());
    }
}
