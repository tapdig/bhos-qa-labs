import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Simple integer addition should work")
    void testAdd() {
        assertEquals(20, calculator.add(5, 15), "Regular addition should work");
    }

    @Test
    @DisplayName("Simple integer subtraction should work")
    void testSubtract() {
        assertEquals(3, calculator.subtract(10, 7), "Regular subtraction should work");
    }

    @Test
    @DisplayName("Simple integer multiplication should work")
    void testMultiply() {
        assertEquals(20, calculator.multiply(4, 5), "Regular multiplication should work");
    }

    @Test
    @DisplayName("Simple integer division should work")
    void testDivide() {
        assertEquals(0, calculator.divide(4, 5), "Regular division should work");
    }

    @Test
    @DisplayName("Simple square root should work")
    void testSquareRoot() {
        assertEquals(4, calculator.squareRoot(16), "Regular square root should work");
    }
}