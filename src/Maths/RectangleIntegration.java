package Maths;

import javafx.util.Pair;

import java.util.function.DoubleUnaryOperator;

public class RectangleIntegration {

    public enum Method {LEFT, MID, RIGHT}

    public static int rungeSteps = 0;
    public static double rungeError = 0;

    public static double runge(Method method, DoubleUnaryOperator fun, Pair<Double, Double> bounds, double prec) {
        int steps = 10;
        double error;
        double result;
        double prev = 0;
        do {
            result = integral(method, fun, bounds, steps);
            error = Math.abs(result - prev) / 3;
            steps *= 2;
            prev = result;
            if (steps > 1000000) throw new ArithmeticException();
        } while (error > prec);
        rungeError = error;
        rungeSteps = steps;
        return result;
    }

    public static double integral(Method method, DoubleUnaryOperator function, Pair<Double, Double> bounds, int steps) {

        // Order bounds, first is lower
        if (bounds.getKey() > bounds.getValue()) bounds = new Pair<>(bounds.getValue(), bounds.getKey());
        Double step = (bounds.getValue() - bounds.getKey()) / steps;
        Double curX = bounds.getKey();
        Double curS;
        Double sum = 0d;

        for (int i = 0; i < steps; i++) {

            if (method == Method.LEFT) curS = function.applyAsDouble(curX) * step;
            else if (method == Method.RIGHT) curS = function.applyAsDouble(curX + step) * step;
            else curS = function.applyAsDouble(curX + step / 2) * step;

            sum += curS;
            curX += step;
        }

        return sum;
    }

}
