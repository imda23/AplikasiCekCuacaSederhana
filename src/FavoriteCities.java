/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author USER
 */
public class FavoriteCities {
    private static final String FAVORITES_FILE = "data/favorites.txt";
    
    // Membaca daftar kota favorit
    public static List<String> loadFavorites() {
        List<String> favorites = new ArrayList<>();
        File file = new File(FAVORITES_FILE);
        
        if (!file.exists()) {
            return favorites;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    favorites.add(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return favorites;
    }
    
    // Menyimpan kota favorit
    public static void saveFavorite(String cityName) throws IOException {
        List<String> favorites = loadFavorites();
        
        // Cek apakah sudah ada
        if (!favorites.contains(cityName)) {
            favorites.add(cityName);
            
            // Buat folder jika belum ada
            File directory = new File("data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Tulis ke file
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(FAVORITES_FILE))) {
                for (String city : favorites) {
                    writer.write(city);
                    writer.newLine();
                }
            }
        }
    }
    
    // Menghapus kota favorit
    public static void removeFavorite(String cityName) throws IOException {
        List<String> favorites = loadFavorites();
        favorites.remove(cityName);
        
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FAVORITES_FILE))) {
            for (String city : favorites) {
                writer.write(city);
                writer.newLine();
            }
        }
    }
    
    // Menyimpan riwayat cuaca ke CSV
    public static void saveWeatherToCSV(WeatherData data) throws IOException {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        File file = new File("data/weather_history.csv");
        boolean isNewFile = !file.exists();
        
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, true))) {
            
            // Tulis header jika file baru
            if (isNewFile) {
                writer.write("City,Country,Temperature,Description,Humidity,WindSpeed,Condition,Timestamp");
                writer.newLine();
            }
            
            // Tulis data
            writer.write(data.toCSV());
            writer.newLine();
        }
    }
    
    // Membaca riwayat cuaca dari CSV
    public static List<String[]> loadWeatherHistory() {
        List<String[]> history = new ArrayList<>();
        File file = new File("data/weather_history.csv");
        
        if (!file.exists()) {
            return history;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                history.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return history;
    }
}
