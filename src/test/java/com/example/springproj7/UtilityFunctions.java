package com.example.springproj7;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;


public class UtilityFunctions {

    public static String get(String targetURL, String idToken) throws IOException {
        URL url = new URL(targetURL);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("GET");
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", "Bearer " + idToken);

        if (http.getResponseCode() == 403) return "Forbidden";

        BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static String post(String targetURL, JSONObject data) throws IOException, InterruptedException, JSONException {
        var values = new HashMap<String, String>();

        for (Iterator<String> it = data.keys(); it.hasNext(); ) {
            String key = it.next();
            values.put(key, data.getString(key));
        }

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(targetURL)).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static void patch(String targetURL, String idToken, String data) throws IOException, InterruptedException {
        URL url = new URL(targetURL);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("X-HTTP-Method-Override", "PATCH");
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");
        http.setRequestProperty("Authorization", "Bearer " + idToken);

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        String response = http.getResponseMessage();
        http.disconnect();
    }

    public static int check(String targetURL, String idToken) throws IOException {
        URL url = new URL(targetURL);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("GET");
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", "Bearer " + idToken);

        return http.getResponseCode();
    }

    public static String uploadFile(String targetUrl, String idToken, String filePath) throws IOException, InterruptedException {
        String requestBody = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            requestBody = sb.toString();
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(targetUrl)).POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Authorization", "Bearer " + idToken).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

}