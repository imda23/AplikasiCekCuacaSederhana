/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class WeatherData {
    private String cityName;
    private String country;
    private double temperature;
    private String description;
    private int humidity;
    private double windSpeed;
    private String weatherCondition; // untuk icon
    private long timestamp;
    
    // Constructor
    public WeatherData(String cityName, String country, double temperature, 
                       String description, int humidity, double windSpeed, 
                       String weatherCondition, long timestamp) {
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.weatherCondition = weatherCondition;
        this.timestamp = timestamp;
    }
    
    // Getters
    public String getCityName() { return cityName; }
    public String getCountry() { return country; }
    public double getTemperature() { return temperature; }
    public String getDescription() { return description; }
    public int getHumidity() { return humidity; }
    public double getWindSpeed() { return windSpeed; }
    public String getWeatherCondition() { return weatherCondition; }
    public long getTimestamp() { return timestamp; }
    
    // Method untuk mendapatkan nama icon
    public String getIconName() {
        String condition = weatherCondition.toLowerCase();
        if (condition.contains("clear") || condition.contains("sunny")) {
            return "sunny.png";
        } else if (condition.contains("cloud")) {
            return "cloudy.png";
        } else if (condition.contains("rain") || condition.contains("drizzle")) {
            return "rainy.png";
        } else if (condition.contains("snow")) {
            return "snowy.png";
        } else if (condition.contains("thunder") || condition.contains("storm")) {
            return "thunderstorm.png";
        }
        return "cloudy.png"; // default
    }
    
    // Method untuk CSV format
    public String toCSV() {
        return String.format("%s,%s,%.2f,%s,%d,%.2f,%s,%d",
                cityName, country, temperature, description, 
                humidity, windSpeed, weatherCondition, timestamp);
    }
}
