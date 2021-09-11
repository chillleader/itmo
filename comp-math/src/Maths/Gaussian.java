package Maths;

import java.util.ArrayList;
import java.util.Collections;

public class Gaussian {

    public static double eliminate(Matrix m) {

        double prod = 1;

        for (int i = 0; i < m.HEIGHT; i++) {

            if (m.at(i, i) == 0) {
                int j = i + 1;
                while (j < m.HEIGHT && m.at(j, i) != 0) j++;
                if (j == m.HEIGHT || m.at(j, i) == 0) {
                    continue;
                }
                m.swap(i, j);
                prod *= -1;
            }

            for (int j = i + 1; j < m.HEIGHT; j++) {
                double k = m.at(j, i) / m.at(i, i);
                m.sub(j, i, k);
            }
        }

        double det = 1;
        for (int i = 0; i < m.HEIGHT; i++) det *= m.at(i, i);
        det /= prod;
        return det;
    }

    public static ArrayList<Double> solve(Matrix em) {

        ArrayList<Double> ans = new ArrayList<>();
        ans.add(em.at(em.HEIGHT - 1, em.WIDTH - 1) / em.at(em.HEIGHT - 1, em.WIDTH - 2));

        for (int i = 1; i < em.HEIGHT; i++) {
            double subVal = 0;
            for (int j = 0; j < i; j++) {
                subVal += (em.at(em.HEIGHT - 1 - i, em.WIDTH - 2 - j) * ans.get(j));
            }
            double nAns = em.at(em.HEIGHT - 1 - i, em.WIDTH - 1) - subVal;
            nAns /= em.at(em.HEIGHT - 1 - i, em.WIDTH - 2 - i);
            ans.add(nAns);
        }
        Collections.reverse(ans);
        return ans;
    }

}
