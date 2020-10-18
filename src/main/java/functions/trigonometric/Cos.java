package functions.trigonometric;

import functions.Function;

public class Cos extends Function {

    private final Sin sin;

    public Cos(Sin sin) {
        this.sin = sin;
    }

    @Override
    public double value(double x) {

        double val = Math.sqrt(1 - Math.pow(sin.value(x), 2));
        if ((2 * Math.abs(x) / Math.PI) % 4 >= 1 && (2 * Math.abs(x) / Math.PI) % 4 < 3) return -val;
        return val;
    }
}
