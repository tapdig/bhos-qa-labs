import java.io.IOException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

public class Calculator {
    public int add(int a, int b) {
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
    public void startCalculator(String cmd) throws IOException {
        Runtime.getRuntime().exec(cmd);
    }
    
    public void runUnsafe(HttpServletRequest request) throws IOException {
        String cmd = request.getParameter("command");
        String arg = request.getParameter("arg");

        Runtime.getRuntime().exec(cmd+" "+arg); // Noncompliant
    }
}
