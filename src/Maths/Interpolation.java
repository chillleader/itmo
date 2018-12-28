package Maths;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Interpolation {

    public static List<List<Double>> getCoef(List<Pair<Double, Double>> points) {
        List<Double> x, y, h, l, delta, lambda, b, c, d;
        int n = points.size() - 1;
        x = new ArrayList<>(Collections.nCopies(n + 1, 0d));
        y = new ArrayList<>(Collections.nCopies(n + 1, 0d));
        h = new ArrayList<>(Collections.nCopies(n + 1, 0d));
        l = new ArrayList<>(Collections.nCopies(n + 1, 0d));
        delta = new ArrayList<>(Collections.nCopies(n + 1, 0d));
        lambda = new ArrayList<>(Collections.nCopies(n + 1, 0d));
        b = new ArrayList<>(Collections.nCopies(n + 1, 0d));
        c = new ArrayList<>(Collections.nCopies(n + 1, 0d));
        d = new ArrayList<>(Collections.nCopies(n + 1, 0d));
        //first we move points to our container
        for (int i = 0; i <= n; i++) {
            x.set(i, points.get(i).getKey());
            y.set(i, points.get(i).getValue());
        }
        //then we set h and l
        for (int i = 1; i <= n; i++) {
            h.set(i, x.get(i) - x.get(i - 1));
            if (h.get(i) == 0) throw new IllegalArgumentException("Equal points");
            l.set(i, (y.get(i) - y.get(i - 1)) / h.get(i));
        }
        //set deltas and lambdas
        delta.set(1, - h.get(2) / 2 / (h.get(2) + h.get(1)));
        lambda.set(1, 1.5 * (l.get(2) - l.get(1)) / (h.get(1) + h.get(2)));
        for (int i = 3; i <= n; i++) {
            delta.set(i - 1, - h.get(i) /
                    (2 * h.get(i - 1) + 2 * h.get(i) + h.get(i - 1) * delta.get(i - 2)));
            lambda.set(i - 1, (3 * l.get(i) - 3 * l.get(i - 1) - h.get(i - 1) * lambda.get(i - 2)) /
                    (2 * h.get(i - 1) + 2 * h.get(i) + h.get(i - 1) * delta.get(i - 2)));
        }
        c.set(0, 0d);
        c.set(n, 0d);
        for (int i = n; i >= 2; i--) {
            c.set(i - 1, delta.get(i - 1) * c.get(i) + lambda.get(i - 1));
        }
        for (int i = 1; i <= n; i++) {
            d.set(i, (c.get(i) - c.get(i - 1)) / 3 / h.get(i));
            b.set(i, l.get(i) + (2 * c.get(i) * h.get(i) + h.get(i) * c.get(i - 1)) / 3);
        }
        //now we have coefficients in y, b, c, d
        List<List<Double>> res = new ArrayList<>();
        List<Double> temp = new ArrayList<>();
        //fill a
        for (int i = 1; i <= n; i++) temp.add(y.get(i));
        res.add(temp);
        //fill b
        temp = new ArrayList<>();
        for (int i = 1; i <= n; i++) temp.add(b.get(i));
        res.add(temp);
        temp = new ArrayList<>();
        for (int i = 1; i <= n; i++) temp.add(c.get(i));
        res.add(temp);
        temp = new ArrayList<>();
        for (int i = 1; i <= n; i++) temp.add(d.get(i));
        res.add(temp);
        return res;
    }

    public static double getInterpolatedValue(List<List<Double>> coefs, List<Pair<Double, Double>> pivots, Double x) {
        int pivotN;
        for (pivotN = 1; pivotN < pivots.size(); pivotN++) {
            if (x <= pivots.get(pivotN).getKey()) break;
        }

        double y = 0;
        y += coefs.get(0).get(pivotN - 1);
        y += coefs.get(1).get(pivotN - 1) * (x - pivots.get(pivotN).getKey());
        y += coefs.get(2).get(pivotN - 1) * Math.pow(x - pivots.get(pivotN).getKey(), 2);
        y += coefs.get(3).get(pivotN - 1) * Math.pow(x - pivots.get(pivotN).getKey(), 3);
        return y;
    }

}
