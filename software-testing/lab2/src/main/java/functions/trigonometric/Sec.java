package functions.trigonometric;

import functions.Function;

public class Sec extends Function {

    private Cos cos;

    public Sec(Cos cos) {
        this.cos = cos;
    }

    @Override
    public double value(double x) {
        return 1 / cos.value(x);
    }
}
