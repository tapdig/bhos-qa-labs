package com.example.springproj8;

import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6Exception;
import com.nimbusds.srp6.SRP6ServerSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}