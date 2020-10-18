package functions.logarithmic;

import functions.Function;

public class Ln extends Function {

    @Override
    public double value(double x) {
        double num, mul, cal, sum = 0;
        num = (x - 1) / (x + 1);

        // terminating value of the loop
        // can be increased to improve the precision
        for (int i = 1; i <= 1000; i++)
        {
            mul = (2 * i) - 1;
            cal = Math.pow(num, mul);
            cal = cal / mul;
            sum = sum + cal;
        }
        sum = 2 * sum;
        return sum;
    }
}
