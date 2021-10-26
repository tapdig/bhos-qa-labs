package com.example.springproj2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.cert.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class Springproj2ApplicationTests {

    public String url = "https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos";

    @Test
    @DisplayName("Integration Test for checking the validity of the SSL certificate of the URL with the hosted certificate")
    public void testSSLCertificateValidity() throws IOException {
        try {
            FileInputStream inputStream  =  new FileInputStream ("certificate.cer");
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate hostedCertificate = (X509Certificate) certFactory.generateCertificate(inputStream);

            URL url = new URL(this.url);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.connect();

            Certificate[] certificates = httpsURLConnection.getServerCertificates();

            assertEquals(hostedCertificate, certificates[0]);

        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Integration Test for checking the response JSON of the URL is list and item parameters (id, name) are unique")
    public void testResponseJSON() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(this.url, String.class);

        JSONArray repos = new JSONArray(response.getBody());

        Set<String> ids = new HashSet<>();
        Set<String> names = new HashSet<>();

        for (int i=0; i < repos.length(); i++) {
            JSONObject repo = (JSONObject) repos.get(i);
            ids.add(repo.getString("id"));
            names.add(repo.getString("name"));
        }

        boolean unique = ids.size() == repos.length() && names.size() == repos.length();

        assertTrue(unique);
    }
}