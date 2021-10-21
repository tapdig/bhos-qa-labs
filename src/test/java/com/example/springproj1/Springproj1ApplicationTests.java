package com.example.springproj1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class Springproj1ApplicationTests {

    public void integrationTest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertNotNull(response);
        assertTrue(response.getStatusCode() == HttpStatus.OK);
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    }

    @DisplayName("Integration Test for the first endpoint")
    @Test
    public void testSuccessfulResponseRepos() {
        integrationTest("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos");
    }

    @DisplayName("Integration Test for the second endpoint")
    @Test
    public void testSuccessfulResponseBranches() {
        integrationTest("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos/1/branches");
    }

    @DisplayName("Integration Test for the third endpoint")
    @Test
    public void testSuccessfulResponseCommits() {
        integrationTest("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos/1/branches/1/commits");
    }
}