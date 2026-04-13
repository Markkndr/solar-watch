package com.codecool.solarwatch.model;

public class SunriseSunset {
    private Results results;
    private String status;

    public Results getResults() { return results; }

    public static class Results {
        private String sunrise;
        private String sunset;

        public String getSunrise() { return sunrise; }
        public String getSunset() { return sunset; }
    }
}
