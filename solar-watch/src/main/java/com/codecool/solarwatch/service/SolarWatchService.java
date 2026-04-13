package com.codecool.solarwatch.service;

import com.codecool.solarwatch.dto.SolarWatchResponse;
import com.codecool.solarwatch.exception.CityNotFoundException;
import com.codecool.solarwatch.model.GeoLocation;
import com.codecool.solarwatch.model.SunriseSunset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

@Service
public class SolarWatchService {

    private final RestTemplate restTemplate;

    @Value("${openweather.api.key}")
    private String apiKey;

    public SolarWatchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SolarWatchResponse getSolarData(String city, String date) {

        // Validate date
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }

        // 1️⃣ Get lat/lon
        String geoUrl = "http://api.openweathermap.org/geo/1.0/direct?q="
                + city + "&limit=1&appid=" + apiKey;

        GeoLocation[] geoResponse = restTemplate.getForObject(geoUrl, GeoLocation[].class);

        if (geoResponse == null || geoResponse.length == 0) {
            throw new CityNotFoundException(city);
        }

        double lat = geoResponse[0].getLat();
        double lon = geoResponse[0].getLon();
        String cityName = geoResponse[0].getName();

        // 2️⃣ Get sunrise/sunset
        String sunUrl = "https://api.sunrise-sunset.org/json?lat="
                + lat + "&lng=" + lon + "&date=" + date + "&formatted=0";

        SunriseSunset sunResponse = restTemplate.getForObject(sunUrl, SunriseSunset.class);

        if (sunResponse == null || sunResponse.getResults() == null) {
            throw new RuntimeException("Sunrise-Sunset API returned no results");
        }

        String sunrise = sunResponse.getResults().getSunrise();
        String sunset = sunResponse.getResults().getSunset();

        // 3️⃣ Get timezone
        String timezoneUrl = "https://api.openweathermap.org/data/2.5/weather?lat="
                + lat + "&lon=" + lon + "&appid=" + apiKey;

        Map<String, Object> weatherResponse = restTemplate.getForObject(timezoneUrl, Map.class);

        Integer timezoneOffset = (Integer) weatherResponse.get("timezone"); // seconds
        String timezone = "UTC" + (timezoneOffset >= 0 ? "+" : "") + (timezoneOffset / 3600);

        return new SolarWatchResponse(
                cityName,
                date,
                sunrise,
                sunset,
                timezone
        );
    }
}