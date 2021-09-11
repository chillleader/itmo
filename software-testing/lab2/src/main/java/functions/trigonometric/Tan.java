package functions.trigonometric;

import functions.Function;

public class Tan extends Function {

    private Sin sin;
    private Cos cos;

    public Tan(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public double value(double x) {
        return sin.value(x) / cos.value(x);
    }
}
