package com.example.springproj8;

import com.nimbusds.srp6.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SRPUnitTest {

    static String username = "test_user";
    static String password = "notsostrongpassword";
    static BigInteger salt = new BigInteger("5486701347560456");

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    public void generateVerifier() {
        SRP6CryptoParams config = SRP6CryptoParams.getInstance();
        SRP6VerifierGenerator gen = new SRP6VerifierGenerator(config);
        BigInteger verifier = gen.generateVerifier(salt, username, password);
    }

    @Test
    @DisplayName("Unit Test to pass SRP Authentication steps with predefined username and password")
    public void SRPAuthenticationUnitTest() throws SRP6Exception {

        // new client session
        SRP6ClientSession Client = new SRP6ClientSession();
        Client.step1(username, password);

        HttpEntity<String> newSesEntity = new HttpEntity<>(username, headers);
        String response = restTemplate.exchange("http://localhost:" + port + "/step1", HttpMethod.POST, newSesEntity, String.class).getBody();

        BigInteger serverPublicB = new BigInteger(response);

        // computing values
        SRP6CryptoParams defaultCryptoParams = SRP6CryptoParams.getInstance();
        SRP6ClientCredentials clientPublicAEvidenceM1 = Client.step2(defaultCryptoParams, salt, serverPublicB);

        ComputeValuesReqBody compValBody = new ComputeValuesReqBody(clientPublicAEvidenceM1.A, clientPublicAEvidenceM1.M1);

        HttpEntity<ComputeValuesReqBody> compValEntity = new HttpEntity<>(compValBody, headers);
        String serverEvidenceM2 = restTemplate.postForEntity("http://localhost:" + port + "/step2", compValEntity, String.class).getBody();

        assertNotEquals("", serverEvidenceM2);
    }
}