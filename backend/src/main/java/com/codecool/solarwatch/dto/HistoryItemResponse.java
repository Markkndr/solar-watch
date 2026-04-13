package com.codecool.solarwatch.dto;

import java.time.Instant;
import java.time.LocalDate;

public record HistoryItemResponse(
        Long id,
        String city,
        String country,
        LocalDate date,
        String sunrise,
        String sunset,
        long dayLengthSeconds,
        Instant createdAt
) {}
