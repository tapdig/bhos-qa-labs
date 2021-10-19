import java.io.IOException;

public class Calculator {
    public int add(int a, int b) {
        SecureRandom sr = new SecureRandom();
        sr.setSeed(123456L); // Noncompliant
        int v = sr.next(32);
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        return a / b;
    }

    public double squareRoot(double a) {
        if (a >= 0)
            return Math.sqrt(a);
        return 0;
    }

    public boolean isOdd(int a) {
        return a % 2 != 0;
    }

    public boolean isEven(int a) {
        return a % 2 == 0;
    }

    public double logarithm(double a) {
        return Math.log(a);
    }

    public double findPowerOfBaseToExponent(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    // intended to be detected as vulnerable by SonarCloud as per Lab4 assignment instructions
    public void startCalculator() throws IOException {
        SecureRandom sr = new SecureRandom();
        sr.setSeed(123456L); // Noncompliant
        int v = sr.next(32);
        Runtime.getRuntime().exec("calc.exe");
    }
}
