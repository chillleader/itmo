package math;

import org.junit.Test;

import static org.junit.Assert.*;

public class PowerSeriesTest {

    private final PowerSeries powerSeries = new PowerSeries();

    private static final double MIN_PRECISION = 0.01;

    @Test
    public void testWithValuesFromRange() {
        for (double x = -0.99; x <= 0.99; x += 0.099) {
            System.out.println("Testing with x = " + x);
            double actual = powerSeries.acos(x);
            double expected = Math.acos(x);
            System.out.println("Actual: " + actual + ", expected: " + expected);
            assertTrue(Math.abs(expected - actual) < MIN_PRECISION);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPositiveXOutOfBounds() {
        powerSeries.acos(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeXOutOfBounds() {
        powerSeries.acos(-3);
    }
}
