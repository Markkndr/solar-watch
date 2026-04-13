package com.codecool.solarwatch.service;

import com.codecool.solarwatch.client.SunriseSunsetApiResponse;
import com.codecool.solarwatch.dto.HistoryItemResponse;
import com.codecool.solarwatch.dto.SolarResponse;
import com.codecool.solarwatch.model.SolarQuery;
import com.codecool.solarwatch.repository.SolarQueryRepository;
import com.codecool.solarwatch.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SolarService {
    private final GeoCodingService geoCodingService;
    private final SunriseSunsetService sunriseSunsetService;
    private final SolarQueryRepository solarQueryRepository;
    private final UserRepository userRepository;

    public SolarService(GeoCodingService geoCodingService,
                        SunriseSunsetService sunriseSunsetService,
                        SolarQueryRepository solarQueryRepository,
                        UserRepository userRepository) {
        this.geoCodingService = geoCodingService;
        this.sunriseSunsetService = sunriseSunsetService;
        this.solarQueryRepository = solarQueryRepository;
        this.userRepository = userRepository;
    }

    public SolarResponse getSolarData(String city, LocalDate date, String username) {
        LocalDate safeDate = date == null ? LocalDate.now() : date;

        CompletableFuture<com.codecool.solarwatch.client.GeoCodingResponse> geoFuture = geoCodingService.getCoordinates(city);

        return geoFuture.thenCompose(geo ->
                sunriseSunsetService.getSolarData(geo.lat(), geo.lon(), safeDate)
                        .thenApply(solar -> mapAndStore(geo, solar, safeDate, username))
        ).join();
    }

    public List<HistoryItemResponse> getHistory(String username) {
        return solarQueryRepository.findTop20ByUserUsernameOrderByCreatedAtDesc(username)
                .stream()
                .map(query -> new HistoryItemResponse(
                        query.getId(),
                        query.getCity(),
                        query.getCountry(),
                        query.getQueryDate(),
                        query.getSunrise(),
                        query.getSunset(),
                        query.getDayLengthSeconds(),
                        query.getCreatedAt()
                ))
                .toList();
    }

    private SolarResponse mapAndStore(com.codecool.solarwatch.client.GeoCodingResponse geo,
                                      SunriseSunsetApiResponse solar,
                                      LocalDate date,
                                      String username) {
        SolarQuery query = new SolarQuery();
        query.setCity(geo.name());
        query.setCountry(geo.country());
        query.setLatitude(geo.lat());
        query.setLongitude(geo.lon());
        query.setQueryDate(date);
        query.setSunrise(solar.results().sunrise());
        query.setSunset(solar.results().sunset());
        query.setDayLengthSeconds(solar.results().dayLength());
        query.setUser(userRepository.findByUsername(username).orElseThrow());
        solarQueryRepository.save(query);

        return new SolarResponse(
                geo.name(),
                geo.country(),
                geo.lat(),
                geo.lon(),
                date,
                solar.results().sunrise(),
                solar.results().sunset(),
                solar.results().dayLength(),
                solar.tzid(),
                true
        );
    }
}
