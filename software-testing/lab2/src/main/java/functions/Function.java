package functions;

import java.io.FileWriter;
import java.io.IOException;

public abstract class Function {

    public abstract double value(double x);

    public void printValues(double initX, double step, double n) {

        String moduleName = this.getClass().getName();
        try (FileWriter csvWriter = new FileWriter(moduleName + ".csv")) {

            csvWriter.append("x,value\n");

            for (int i = 0; i < n; i++) {
                csvWriter
                        .append(String.valueOf(initX))
                        .append(",")
                        .append(String.valueOf(value(initX)))
                        .append("\n");
                initX += step;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
