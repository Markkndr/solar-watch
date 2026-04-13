package com.codecool.solarwatch.service;

import com.codecool.solarwatch.client.SunriseSunsetApiResponse;
import com.codecool.solarwatch.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Service
public class SunriseSunsetService {
    private final WebClient webClient;
    private final String baseUrl;

    public SunriseSunsetService(WebClient webClient,
                                @Value("${app.api.sunrise-sunset-url}") String baseUrl) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    @Async
    public CompletableFuture<SunriseSunsetApiResponse> getSolarData(double latitude, double longitude, LocalDate date) {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("lat", latitude)
                .queryParam("lng", longitude)
                .queryParam("date", date)
                .queryParam("formatted", 0)
                .toUriString();

        var response = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(SunriseSunsetApiResponse.class)
                .block();

        if (response == null || response.results() == null) {
            throw new ApiException("Failed to load solar data");
        }

        return CompletableFuture.completedFuture(response);
    }
}
