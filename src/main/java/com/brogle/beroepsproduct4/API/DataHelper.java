package com.brogle.beroepsproduct4.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Jens
 */
public class DataHelper {

    //private StringBuilder stringBuilder = new StringBuilder();

    private final String URL = "http://bp4.local/data";

    public String getJSONData() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                try ( BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                }
            } else {
                throw new RuntimeException("Response code was not 200; it was " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("Failed to retrieve JSON data: " + e.getMessage());
            return "{\"tolong\":false,\"turns\":0,\"lastturn\":0}";
        } catch (RuntimeException re) {
            System.out.println(":(");
        }
        return stringBuilder.toString();
    }
}
