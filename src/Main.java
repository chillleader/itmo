import Maths.Gaussian;
import Maths.Matrix;
import Maths.RectangleIntegration;
import Maths.Interpolation;
import gui.MainGui;
import input.MatrixParser;
import input.RandMatrix;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.log;

public class Main {

    public static void main(String args[]) {

        MainGui mainGui = new MainGui();

        MatrixParser mp = MatrixParser.INSTANCE;

        List<Pair<Double, Double>> t = new ArrayList<>();
        t.add(new Pair<>(1d, 5d));
        t.add(new Pair<>(2d, 3d));
        t.add(new Pair<>(3d, 2.5));
        t.add(new Pair<>(4d, 2d));
        t.add(new Pair<>(5d, 0d));
        List<List<Double>> a = Interpolation.getCoef(t);
        System.out.println(Interpolation.getInterpolatedValue(a, t, 4.9));
    }
}