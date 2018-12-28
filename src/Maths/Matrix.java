package Maths;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matrix {

    final public int WIDTH;
    final public int HEIGHT;
    final public ArrayList<ArrayList<Double>> matrix;

    public int rank;

    public Matrix(int height, int width, ArrayList<ArrayList<Double>> list) {
        WIDTH = width;
        HEIGHT = height;
        matrix = list;
        rank = HEIGHT;
    }

    public double at(int line, int col) {
        return matrix.get(line).get(col);
    }

    public void set(double val, int line, int col) {
        matrix.get(line).set(col, val);
    }

    public void swap(int line1, int line2) {
        Collections.swap(matrix, line1, line2);
    }

    public void swapCols(int col1, int col2) {
        for (int i = 0; i < HEIGHT; i++) {
            double t = at(i, col1);
            set(at(i, col2), i, col1);
            set(t, i, col2);
        }
    }

    public void sub(int subtracted, int subtractor, double k) {
        for (int i = 0; i < WIDTH; i++) {
            double nVal = at(subtracted, i) - at(subtractor, i) * k;
            set(nVal, subtracted, i);
        }
    }

    public int getCompleteRank() {
        return rank;
    }

    public int getIncompleteRank() {
        return rank;
    }

    public boolean hasSolutions() {
        return getCompleteRank() == getIncompleteRank();
    }

    public int solutionsNumber() {
        if (!hasSolutions()) return 0;
        if (getIncompleteRank() == HEIGHT) return 1;
        return Integer.MAX_VALUE;
    }

    public Matrix transpond() {
        ArrayList<ArrayList<Double>> nm = new ArrayList<>();
        for (int i = 0; i < WIDTH; i++) {
            nm.add(new ArrayList<>());
            for (int j = 0; j < HEIGHT; j++) {
                nm.get(i).add(at(j, i));
            }
        }
        return new Matrix(WIDTH, HEIGHT, nm);
    }

    public ArrayList<Double> getDiscrepancies(ArrayList<Double> solutions) {
        ArrayList<Double> ans = new ArrayList<>();
        for (ArrayList<Double> line : matrix) {
            double sum = 0;
            for (int i = 0; i < HEIGHT; i++) sum += line.get(i) * solutions.get(i);
            ans.add(sum - line.get(HEIGHT));
        }
        return ans;
    }
}
