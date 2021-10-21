package com.example.springproj1;

import org.json.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class Springproj1ApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    public boolean isResponseJSON(String response) {
        try {
            new JSONObject(response);
        } catch (JSONException e) {
            try {
                new JSONArray(response);
            } catch (JSONException ex) {
                return false;
            }
        }
        return true;
    }

    @DisplayName("Integration Test for the first endpoint")
    @Test
    public void testSuccessfulResponseRepos() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos", String.class);

        assertThat(response).isNotNull();
        assertTrue(isResponseJSON(response.getBody()));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("Integration Test for the second endpoint")
    @Test
    public void testSuccessfulResponseBranches() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos/1/branches", String.class);

        assertThat(response).isNotNull();
        assertTrue(isResponseJSON(response.getBody()));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("Integration Test for the third endpoint")
    @Test
    public void testSuccessfulResponseCommits() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos/1/branches/1/commits", String.class);

        assertThat(response).isNotNull();
        assertTrue(isResponseJSON(response.getBody()));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}