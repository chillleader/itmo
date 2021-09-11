package gui;

import Maths.Interpolation;
import Maths.RectangleIntegration;
import java.text.DecimalFormat;
import javafx.util.Pair;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.*;
import org.knowm.xchart.internal.series.MarkerSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class InterpolationGUI extends JPanel {

    private final int STEPS = 100;

    private JLabel desc = new JLabel("Интерполяция кубическими сплайнами");
    private JPanel buttons = new JPanel();
    private JPanel graph = new JPanel();
    private JPanel explore = new JPanel();

    private JButton lessPoints = new JButton("Мало точек, 0-2PI");
    private JButton morePoints = new JButton("Много точек, 0-2PI");
    private JButton wrongPoints = new JButton("Точка с отклонением");
    private JButton intervalPoints = new JButton("Интервал 0-50PI");

    private JTextField xValue = new JTextField("X", 12);
    private JTextField yValue = new JTextField("Y", 12);
    private JTextArea points = new JTextArea(10, 12);
    private JLabel enterX = new JLabel("Введите X:");
    private JLabel enterY = new JLabel("Y(X):");
    private JButton calcButton = new JButton("Считать");

    private List<List<Double>> lastCoefs;
    private List<Pair<Double, Double>> lastPivots;

    private XYChart chart;

    private List<Double> x0, x1, x2, x3, y0, y1, y2, y3;

    int chosenSet = 0;

    InterpolationGUI() {
        super();
        this.setLayout(new BorderLayout());
        setPoints();
        setListeners();
        initComponents();
        placeComponents();
    }

    private void initComponents() {
        buttons.setPreferredSize(new Dimension(250, 500));
        buttons.setBackground(Color.GRAY);
        buttons.setBorder(BorderFactory.createLineBorder(Color.black));
        buttons.setLayout(new FlowLayout());
        lessPoints.setPreferredSize(new Dimension(240, 100));
        morePoints.setPreferredSize(new Dimension(240, 100));
        wrongPoints.setPreferredSize(new Dimension(240, 100));
        intervalPoints.setPreferredSize(new Dimension(240, 100));
        buttons.add(lessPoints);
        buttons.add(morePoints);
        buttons.add(wrongPoints);
        buttons.add(intervalPoints);
        graph.setPreferredSize(new Dimension(710, 500));
        graph.add(doGraph());
        graph.setBackground(Color.LIGHT_GRAY);
        graph.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        explore.setPreferredSize(new Dimension(250, 500));
        explore.setBackground(Color.GRAY);
        explore.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        enterX.setFont(new Font("Verdana", Font.BOLD, 20));
        enterY.setFont(new Font("Verdana", Font.BOLD, 20));
        points.setBackground(Color.GRAY);
        explore.add(enterX);
        explore.add(xValue);
        explore.add(calcButton);
        //explore.add(enterY);
        yValue.setEditable(false);
        explore.add(yValue);
        points.setEditable(false);
        points.setFont(new Font("Verdana", Font.PLAIN, 20));
        //points.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        explore.add(points);
    }

    private void placeComponents() {
        this.add(buttons, BorderLayout.LINE_START);
        this.add(graph, BorderLayout.CENTER);
        this.add(explore, BorderLayout.LINE_END);
    }

    private JPanel doGraph() {
        java.util.List<Pair<Double, Double>> t = new ArrayList<>();
        /*t.add(new Pair<>(1d, 5d));
        t.add(new Pair<>(2d, 3d));
        t.add(new Pair<>(3d, 2.5));
        t.add(new Pair<>(4d, 2d));
        t.add(new Pair<>(5d, 0d));
        t.add(new Pair<>(6d, 7d));*/
        switch (chosenSet) {
            case 0:
                for (int i = 0; i < x0.size(); i++) {
                    t.add(new Pair<>(x0.get(i), y0.get(i)));
                }
                break;
            case 1:
                for (int i = 0; i < x1.size(); i++) {
                    t.add(new Pair<>(x1.get(i), y1.get(i)));
                }
                break;
            case 2:
                for (int i = 0; i < x2.size(); i++) {
                    t.add(new Pair<>(x2.get(i), y2.get(i)));
                }
                break;
            case 3:
                for (int i = 0; i < x3.size(); i++) {
                    t.add(new Pair<>(x3.get(i), y3.get(i)));
                }
                break;
            default: return null;
        }
        lastPivots = t;
        double[] xData = new double[STEPS + 1];
        double[] yData = new double[STEPS + 1];
        lastCoefs = Interpolation.getCoef(t);
        double step = (t.get(t.size() - 1).getKey() - t.get(0).getKey()) / STEPS;
        double xval = t.get(0).getKey();
        double yval = 0;
        for (int i = 0; i < STEPS + 1; i++) {
            yval = Interpolation.getInterpolatedValue(lastCoefs, t, xval);
            xData[i] = xval;
            yData[i] = yval;
            xval += step;
        }
        //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        chart = new XYChartBuilder().width(700).height(500).title("Графики").xAxisTitle("X").yAxisTitle("Y").build();
        XYSeries s = chart.addSeries("Интерп.", xData, yData);
        s.setLineWidth(5f);
        s.setMarker(SeriesMarkers.NONE);
        List<Double> xDots = new ArrayList<>();
        List<Double> yDots = new ArrayList<>();
        for (Pair<Double, Double> p : lastPivots) {
            xDots.add(p.getKey());
            yDots.add(p.getValue());
        }
        XYSeries dots = chart.addSeries("Dots", xDots, yDots);
        dots.setShowInLegend(false);
        dots.setLineWidth(0.0001f);
        List<Double> sinX = new ArrayList<>();
        List<Double> sinY = new ArrayList<>();
        xval = t.get(0).getKey();
        String pointsText = "Опорные точки:\n";
        DecimalFormat df = new DecimalFormat("#0.##");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < xDots.size(); i++) {
            sb.append("X = ");
            sb.append(df.format(xDots.get(i)));
            sb.append("; Y = ");
            sb.append(df.format(yDots.get(i)));
            sb.append("\n");
        }
        pointsText += sb.toString();
        points.setText(pointsText);
        for (int i = 0; i < STEPS + 1; i++) {
            yval = Math.sin(xval);
            sinX.add(xval);
            sinY.add(yval);
            xval += step;
        }
        points.setText(pointsText);
        XYSeries sin = chart.addSeries("sin(x)", sinX, sinY);
        sin.setLineWidth(2f);
        sin.setMarker(SeriesMarkers.NONE);
        sin.setLineColor(Color.RED);

        //dots.set
        // Show it
        //sw = new SwingWrapper<>(chart);
        return new XChartPanel<>(chart);
    }

    private void setListeners() {
        ActionListener methodListener = e -> {
            chosenSet = 0;
            lessPoints.setEnabled(false);
            morePoints.setEnabled(true);
            wrongPoints.setEnabled(true);
            intervalPoints.setEnabled(true);
            repaintGraph();
        };
        lessPoints.addActionListener(methodListener);
        methodListener = e -> {
            chosenSet = 1;
            lessPoints.setEnabled(true);
            morePoints.setEnabled(false);
            wrongPoints.setEnabled(true);
            intervalPoints.setEnabled(true);
            repaintGraph();
        };
        morePoints.addActionListener(methodListener);
        methodListener = e -> {
            chosenSet = 2;
            lessPoints.setEnabled(true);
            morePoints.setEnabled(true);
            wrongPoints.setEnabled(false);
            intervalPoints.setEnabled(true);
            repaintGraph();
        };
        wrongPoints.addActionListener(methodListener);
        methodListener = e -> {
            chosenSet = 3;
            lessPoints.setEnabled(true);
            morePoints.setEnabled(true);
            wrongPoints.setEnabled(true);
            intervalPoints.setEnabled(false);
            repaintGraph();
        };
        intervalPoints.addActionListener(methodListener);
        methodListener = e -> {
            try {
                getYValue(Double.parseDouble(xValue.getText()));
            } catch (NumberFormatException ex) {
                yValue.setText("Некорректный ввод");
            }
        };
        calcButton.addActionListener(methodListener);
    }

    private void repaintGraph() {
        graph.removeAll();
        JPanel p = doGraph();
        graph.add(p);
        p.revalidate();
        p.repaint();
        //sw.repaintChart();
    }

    private void getYValue(double x) {
        try {
            Double d = Interpolation.getInterpolatedValue(lastCoefs, lastPivots, x);
            DecimalFormat format = new DecimalFormat("#0.0000");
            yValue.setText(format.format(d));
        } catch (Exception e) {
            yValue.setText("Некорректный ввод");
        }

    }

    private void setPoints() {
        x0 = new ArrayList<>();
        y0 = new ArrayList<>();
        x0.add(Math.PI / 2);
        x0.add(Math.PI);
        x0.add(Math.PI * 3 / 2);
        x0.add(Math.PI * 2);
        y0.add(1d);
        y0.add(0d);
        y0.add(-1d);
        y0.add(0d);

        x1 = new ArrayList<>();
        y1 = new ArrayList<>();
        x1.add(0d);
        x1.add(Math.PI / 4);
        x1.add(Math.PI / 2);
        x1.add(Math.PI * 3 / 4);
        x1.add(Math.PI);
        x1.add(Math.PI * 5 / 4);
        x1.add(Math.PI * 3 / 2);
        x1.add(Math.PI * 7 / 4);
        x1.add(Math.PI * 2);
        y1.add(0d);
        y1.add(Math.sqrt(2) / 2);
        y1.add(1d);
        y1.add(Math.sqrt(2) / 2);
        y1.add(0d);
        y1.add(-Math.sqrt(2) / 2);
        y1.add(-1d);
        y1.add(-Math.sqrt(2) / 2);
        y1.add(0d);


        x2 = new ArrayList<>(x1);
        y2 = new ArrayList<>(y1);
        y2.set(4, 5d);

        x3 = new ArrayList<>();
        y3 = new ArrayList<>();
        x3.add(0d);
        x3.add(6.25 * Math.PI);
        x3.add(10 * Math.PI);
        x3.add(13.4 * Math.PI);
        x3.add(25.7 * Math.PI);
        x3.add(33.1 * Math.PI);
        x3.add(38.8 * Math.PI);
        x3.add(45 * Math.PI);
        x3.add(50 * Math.PI);

        y3.add(0d);
        y3.add(Math.sqrt(2) / 2);
        y3.add(0d);
        y3.add(-0.95105651629);
        y3.add(-0.80901699437);
        y3.add(-0.30901699437);
        y3.add(0.58778525229);
        y3.add(0d);
        y3.add(0d);
    }

}
