package com.codecool.solarwatch.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SunriseSunsetApiResponse(Results results, String status, String tzid) {
    public record Results(
            String sunrise,
            String sunset,
            @JsonProperty("day_length") long dayLength
    ) {}
}
