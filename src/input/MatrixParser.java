package input;

import Maths.*;

import java.util.ArrayList;

public class MatrixParser {

    public final static MatrixParser INSTANCE = new MatrixParser();

    public Matrix parse(String s) {

        ArrayList<ArrayList<Double>> list = new ArrayList<>();
        for (String val : s.split("[\\n]")) {
            val = val.trim();
            ArrayList<Double> line = new ArrayList<>();
            for (String _val : val.split(" ")) {
                line.add(Double.parseDouble(_val.trim()));
            }
            list.add(line);
        }
        return new Matrix(list.size(), (list.size() > 0 ? list.get(0).size() : 0), list);
    }

}
