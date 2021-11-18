package com.example.springproj5;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class UserServiceTests {

    String json = new String(Files.readAllBytes(Paths.get("./firebase.json")));
    JSONObject key = new JSONObject(json);

    String EMAIL = key.getString("FIREBASE_EMAIL");
    String PASSWORD = key.getString("FIREBASE_PASSWORD");
    String FIREBASE_WEB_API_KEY = key.getString("FIREBASE_WEB_API_KEY");
    String FIREBASE_SIGNIN_URL = key.getString("FIREBASE_SIGNIN_URL");
    String FIRESTORE_USERS_COLLECTION_URL = key.getString("FIRESTORE_USERS_COLLECTION_URL");

    UserServiceTests() throws IOException, JSONException {
    }

    public String authentication() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        JSONObject data = new JSONObject();
        data.put("email", EMAIL);
        data.put("password", PASSWORD);
        data.put("returnSecureToken", true);

        HttpEntity<String> entity = new HttpEntity<>(data.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(FIREBASE_SIGNIN_URL+FIREBASE_WEB_API_KEY, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    @Test
    @DisplayName("End-to-End Test")
    public void EndToEndTest() throws ExecutionException, InterruptedException, JSONException {

        String response = authentication();
        JSONObject jsonResponse = new JSONObject(response);

        // Firebase Auth ID token for the authenticated user
        String idToken = jsonResponse.getString("idToken");

        // UID of the authenticated user
        String localId = jsonResponse.getString("localId");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", Collections.singletonList("Bearer ".concat(idToken)));

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> userResponse = restTemplate.exchange(FIRESTORE_USERS_COLLECTION_URL+localId, HttpMethod.GET, entity, String.class);

        JSONObject userDetails = new JSONObject(userResponse.getBody());
        JSONObject fields = userDetails.getJSONObject("fields");

        String name = fields.getJSONObject("name").getString("stringValue");
        String surname = fields.getJSONObject("surname").getString("stringValue");
        String phone = fields.getJSONObject("phone").getString("stringValue");

        User testUser = new User("T", "M", "+994001112233");

        assertEquals(name, testUser.name);
        assertEquals(surname, testUser.surname);
        assertEquals(phone, testUser.phone);
    }
}