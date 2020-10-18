package functions.logarithmic;

import functions.Function;

public class Log5 extends Function {

    private Ln ln;

    public Log5(Ln ln) {
        this.ln = ln;
    }

    @Override
    public double value(double x) {
        return ln.value(x) / ln.value(5);
    }
}
