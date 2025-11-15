/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author USER
 */
public class WeatherAPI {
    private static final String API_KEY = "5f7e81f1f0bab5d7b3c6fbd6c61715f8";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";
    
    public static WeatherData getWeatherData(String cityName) throws Exception {
        // Buat URL dengan parameter
        String urlString = String.format("%s?q=%s&appid=%s&units=metric&lang=id", 
                                        API_URL, cityName, API_KEY);
        
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        
        int responseCode = conn.getResponseCode();
        
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            // Parse JSON
            return parseWeatherData(response.toString());
        } else {
            throw new Exception("Kota tidak ditemukan atau API error. Kode: " + responseCode);
        }
    }
    
    private static WeatherData parseWeatherData(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        
        String cityName = json.getString("name");
        String countryCode = json.getJSONObject("sys").getString("country"); // "ID"
        
        String countryName = getCountryName(countryCode);
        
        double temperature = json.getJSONObject("main").getDouble("temp");
        int humidity = json.getJSONObject("main").getInt("humidity");
        double windSpeed = json.getJSONObject("wind").getDouble("speed");
        
        JSONArray weatherArray = json.getJSONArray("weather");
        JSONObject weather = weatherArray.getJSONObject(0);
        String description = weather.getString("description");
        String weatherCondition = weather.getString("main");
        
        long timestamp = System.currentTimeMillis();
        
        return new WeatherData(cityName, countryName, temperature, description, 
                          humidity, windSpeed, weatherCondition, timestamp);
    }
    
    private static String getCountryName(String countryCode) {
        switch (countryCode.toUpperCase()) {
            case "ID": return "Indonesia";
            case "US": return "United States";
            case "GB": return "United Kingdom";
            case "JP": return "Japan";
            case "CN": return "China";
            case "AU": return "Australia";
            case "MY": return "Malaysia";
            case "SG": return "Singapore";
            case "TH": return "Thailand";
            case "PH": return "Philippines";
            case "VN": return "Vietnam";
            case "IN": return "India";
            case "FR": return "France";
            case "DE": return "Germany";
            case "IT": return "Italy";
            case "ES": return "Spain";
            case "BR": return "Brazil";
            case "RU": return "Russia";
            case "KR": return "South Korea";
            case "CA": return "Canada";
            case "MX": return "Mexico";
            case "NL": return "Netherlands";
            case "SE": return "Sweden";
            case "NO": return "Norway";
            case "DK": return "Denmark";
            case "FI": return "Finland";
            case "NZ": return "New Zealand";
            case "ZA": return "South Africa";
            case "EG": return "Egypt";
            case "SA": return "Saudi Arabia";
            case "AE": return "United Arab Emirates";
            default: return countryCode; // Jika tidak ada, tampilkan code
        }
    }
}
