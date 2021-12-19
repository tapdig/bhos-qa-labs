package com.example.springproj8;

import com.nimbusds.srp6.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

@RestController
public class AuthService {

    static String salt = "5486701347560456";
    static String verifier = "6767204271782158860771322229044513716412960751120553219637749143978708563343782316896185472711068761617527035250432255495604010400219986682191589592880894";

    public static SRP6CryptoParams config;
    public static SRP6ServerSession serverSession;

    @PostMapping("/step1")
    public String step1(@RequestBody String username) {

        config = SRP6CryptoParams.getInstance();
        serverSession = new SRP6ServerSession(config);

        BigInteger S = new BigInteger(salt);
        BigInteger V = new BigInteger(verifier);
        String serverPublicValueB = serverSession.step1(username, S, V).toString();

        return serverPublicValueB;
    }

    @PostMapping("/step2")
    public String step2(@RequestBody ComputeValuesReqBody computeValuesReqBody) {
        try {
            return serverSession.step2(computeValuesReqBody.A, computeValuesReqBody.M1).toString();
        } catch (SRP6Exception e) {
            return "";
        }
    }

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @GetMapping("/srpLoadTest")
    public ResponseEntity<Object> srpLoadTest() throws SRP6Exception {
        SRP6ClientSession Client = new SRP6ClientSession();
        String username = "test_user";
        String password = "notsostrongpassword";
        Client.step1(username, password);

        HttpEntity<String> newSesEntity = new HttpEntity<>(username, headers);
        String response = restTemplate.exchange("http://localhost:8080/step1", HttpMethod.POST, newSesEntity, String.class).getBody();

        BigInteger serverPublicB = new BigInteger(response);

        SRP6CryptoParams defaultCryptoParams = SRP6CryptoParams.getInstance();
        BigInteger saltS = new BigInteger(salt);
        SRP6ClientCredentials clientPublicAEvidenceM1 = Client.step2(defaultCryptoParams, saltS, serverPublicB);

        ComputeValuesReqBody compValBody = new ComputeValuesReqBody(clientPublicAEvidenceM1.A, clientPublicAEvidenceM1.M1);
        HttpEntity<ComputeValuesReqBody> compValEntity = new HttpEntity<>(compValBody, headers);

        String serverEvidenceM2 = restTemplate.postForEntity("http://localhost:8080/step2", compValEntity, String.class).getBody();

        if (serverEvidenceM2 == "") {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(serverEvidenceM2);
        }
    }
}