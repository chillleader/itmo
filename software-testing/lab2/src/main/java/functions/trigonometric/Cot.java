package functions.trigonometric;

import functions.Function;

public class Cot extends Function {

    private Sin sin;
    private Cos cos;

    public Cot(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public double value(double x) {
        return cos.value(x) / sin.value(x);
    }
}
