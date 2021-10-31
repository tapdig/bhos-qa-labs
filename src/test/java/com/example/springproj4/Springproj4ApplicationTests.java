package com.example.springproj4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class Springproj4ApplicationTests {

    public void integrationTest(String url) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        JSONObject responseBody = new JSONObject(response.getBody());
        JSONArray books = responseBody.getJSONArray("results");

        boolean nonEmptyTitle = true;

        ArrayList<Integer> ranks = new ArrayList<Integer>();
        ArrayList<Integer> ranksArray = new ArrayList<Integer>();

        for (int i=0; i<books.length(); i++) {
            JSONObject book = (JSONObject) books.get(i);
            JSONArray bookDetails = book.getJSONArray("book_details");
            JSONObject details = (JSONObject) bookDetails.get(0);

            String title = details.getString("title");
            if (title.isEmpty()) {
                nonEmptyTitle = false;
                break;
            }

            int rank = (int) book.get("rank");
            ranks.add(rank);
            ranksArray.add(i+1);
        }

        Collections.sort(ranks);
        boolean completeRanking = true;

        for (int i=0; i < ranks.size(); i++) {
            if (ranks.get(i) != ranksArray.get(i)) {
                completeRanking = false;
                break;
            }
        }

        boolean result = nonEmptyTitle && completeRanking;
        assertTrue(result);
    }

    @Test
    @DisplayName("Integration Test for NYTimes Books API")
    public void testSuccessfulResponse() throws JSONException, IOException {
        String json = new String(Files.readAllBytes(Paths.get("./api_key.json")));
        JSONObject key = new JSONObject(json);
        String apiKey = key.getString("API_KEY_NYTimes");

        integrationTest("https://api.nytimes.com/svc/books/v3/lists.json?list=combined-print-and-e-book-nonfiction&api-key=" + apiKey);
    }
}