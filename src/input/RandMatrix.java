package input;

import Maths.Matrix;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class RandMatrix {

    private static int BOUND = 100;

    public static String generate(int size) {


        BOUND = 100 / size;

        ArrayList<ArrayList<Integer>> m = new ArrayList<>();
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            m.add(new ArrayList<>());
            for (int j = 0; j <= size; j++) {
                if (j == i) m.get(i).add(1);
                else if (j == size) {
                    m.get(i).add(r.nextInt(BOUND) + 1);
                }
                else m.get(i).add(0);
            }
            int mult = r.nextInt(BOUND) + 1;
            for (int j = 0; j <= size; j++) m.get(i).set(j, m.get(i).get(j) * mult);
        }

        for (int i = 0; i < size * 2; i++) {
            for (int j = 0; j < size; j++) {
                if (i % size == j) continue;
                int mult = r.nextInt(4) - 2;
                for (int k = 0; k <= size; k++) {
                    m.get(j).set(k, m.get(j).get(k) + m.get(i % size).get(k) * mult);
                }
            }

        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= size; j++) {
                sb.append(m.get(i).get(j));
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
