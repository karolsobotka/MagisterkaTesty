package unitTest;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    public void testAddition() {
        assertEquals(calculator.add(2, 3), 5);
    }

    @Test
    public void testSubtraction() {
        assertEquals(calculator.subtract(10, 4), 6);
    }

    @Test
    public void testMultiplication() {
        assertEquals(calculator.multiply(3, 4), 12);
    }

    @Test
    public void testDivision() {
        assertEquals(calculator.divide(10, 2), 5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDivisionByZero() {
        calculator.divide(5, 0);
    }
}
