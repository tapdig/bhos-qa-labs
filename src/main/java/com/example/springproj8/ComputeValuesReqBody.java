package com.example.springproj8;

import java.math.BigInteger;

public class ComputeValuesReqBody {
    public BigInteger A;
    public BigInteger M1;

    public ComputeValuesReqBody(BigInteger A, BigInteger M1) {
        this.A = A;
        this.M1 = M1;
    }
}