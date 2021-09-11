package functions;

import functions.logarithmic.Log10;
import functions.logarithmic.Log2;
import functions.logarithmic.Log5;
import functions.trigonometric.*;

public class FunctionSystem extends Function {

    private final Cos cos;
    private final Sec sec;
    private final Cot cot;
    private final Tan tan;
    private final Log2 log2;
    private final Log5 log5;
    private final Log10 log10;

    public FunctionSystem(Cos cos, Sec sec, Tan tan, Cot cot,
                          Log2 log2, Log5 log5, Log10 log10) {
        this.cos = cos;
        this.sec = sec;
        this.tan = tan;
        this.cot = cot;
        this.log2 = log2;
        this.log5 = log5;
        this.log10 = log10;
    }

    @Override
    public double value(double x) {

        if (x <= 0) return trigonometric(x);
        else return logarithmic(x);
    }

    /**
     * (((((cot(x) + sec(x)) - tan(x)) / cos(x)) ^ 2) ^ 2)
     */
    private double trigonometric(double x) {

        return Math.pow((cot.value(x) + sec.value(x) - tan.value(x)) / cos.value(x), 4);
    }

    /**
     * (((((log_5(x) * log_10(x)) ^ 2) - log_10(x)) - log_5(x)) / log_2(x))
     */
    private double logarithmic(double x) {

        return ( Math.pow(log5.value(x) * log10.value(x), 2) - log10.value(x) - log5.value(x) )
                / log2.value(x);
    }
}
