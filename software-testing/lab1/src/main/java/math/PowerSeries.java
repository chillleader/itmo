package math;

public class PowerSeries {

    private static final int MAX_N = 50;

    public double acos(double x) {
        if (Math.abs(x) > 1) throw new IllegalArgumentException("X must be in bounds (-1; 1)");

        double function = 0;

        for (int n = 0; n < MAX_N; n++) {
            function += ((factorial(2 * n)) / (Math.pow(4, n) * Math.pow(factorial(n), 2) * (2 * n + 1)))
                    * Math.pow(x, 2 * n + 1);
        }

        return (Math.PI / 2) - function;
    }

    private double factorial(int fact) {
        if (fact == 0)
            return 1;
        return fact * factorial(fact-1);
    }
}
