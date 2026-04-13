package com.codecool.solarwatch.dto;

public class SolarWatchResponse {

    private String city;
    private String date;
    private String sunrise;
    private String sunset;
    private String timezone;

    public SolarWatchResponse(String city, String date, String sunrise, String sunset, String timezone) {
        this.city = city;
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.timezone = timezone;
    }

    public String getCity() { return city; }
    public String getDate() { return date; }
    public String getSunrise() { return sunrise; }
    public String getSunset() { return sunset; }
    public String getTimezone() { return timezone; }
}
