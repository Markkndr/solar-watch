package com.codecool.solarwatch.dto;

import java.time.LocalDate;

public record SolarResponse(
        String city,
        String country,
        double latitude,
        double longitude,
        LocalDate date,
        String sunrise,
        String sunset,
        long dayLengthSeconds,
        String timezone,
        boolean stored
) {}
