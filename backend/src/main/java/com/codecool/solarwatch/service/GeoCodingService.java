package com.codecool.solarwatch.service;

import com.codecool.solarwatch.client.GeoCodingResponse;
import com.codecool.solarwatch.exception.ApiException;
import com.codecool.solarwatch.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class GeoCodingService {
    private final WebClient webClient;
    private final String baseUrl;

    public GeoCodingService(WebClient webClient,
                            @Value("${app.api.open-meteo-geocoding-url}") String baseUrl) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    @Async
    public CompletableFuture<GeoCodingResponse> getCoordinates(String city) {
        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("name", city)
                .queryParam("count", 1)
                .queryParam("language", "en")
                .queryParam("format", "json")
                .toUriString();

        var response = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GeocodingApiWrapper.class)
                .block();

        if (response == null) {
            throw new ApiException("Failed to load geocoding data");
        }
        if (response.results() == null || response.results().isEmpty()) {
            throw new ResourceNotFoundException("City not found: " + city);
        }
        return CompletableFuture.completedFuture(response.results().getFirst());
    }

    private record GeocodingApiWrapper(List<GeoCodingResponse> results) {}
}
