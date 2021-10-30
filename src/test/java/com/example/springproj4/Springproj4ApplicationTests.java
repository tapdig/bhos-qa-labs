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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class Springproj4ApplicationTests {

    public static boolean isConsecutive(int[] A) {
        if (A.length <= 1) {
            return true;
        }

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        for (int i: A) {
            if (i < min) { min = i; }
            if (i > max) { max = i; }
        }

        if (max - min != A.length - 1) {
            return false;
        }

        Set<Integer> visited = new HashSet<>();

        for (int i: A) {
            if (visited.contains(i)) {
                return false;
            }
            visited.add(i);
        }

        return true;
    }

    public void integrationTest(String url) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        JSONObject responseBody = new JSONObject(response.getBody());
        JSONArray books = responseBody.getJSONArray("results");

        boolean nonEmptyTitle = true;

        int[] ranks = {};

        for (int i=0; i<books.length(); i++) {
            JSONObject book = (JSONObject) books.get(i);
            JSONArray bookDetails = book.getJSONArray("book_details");
            JSONObject details = (JSONObject) bookDetails.get(0);

            int rank = (int) book.get("rank");
            String title = details.getString("title");

            // System.out.println(rank + ". " + title);

            nonEmptyTitle = !title.isEmpty();

            ranks = Arrays.copyOf(ranks, ranks.length + 1);
            ranks[ranks.length-1] = rank;
        }

        boolean completeRanking = isConsecutive(ranks);
        boolean result = (nonEmptyTitle && completeRanking);

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