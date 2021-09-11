package functions.logarithmic;

import functions.Function;

public class Log2 extends Function {

    private Ln ln;

    public Log2(Ln ln) {
        this.ln = ln;
    }

    @Override
    public double value(double x) {
        return ln.value(x) / ln.value(2);
    }
}
