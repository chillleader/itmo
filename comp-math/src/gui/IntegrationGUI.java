package gui;

import Maths.RectangleIntegration;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.function.DoubleUnaryOperator;

public class IntegrationGUI extends JPanel {

    private JLabel desc = new JLabel("Интегрирование методом прямоугольников");

    private JPanel boundsChoice = new JPanel();
    private JTextField lowerBound = new JTextField(10);
    private JTextField upperBound = new JTextField(10);
    private JLabel boundChoiceLabel = new JLabel("Пределы интегрирования");

    private JPanel preciseChoice = new JPanel();
    private Double[] items = {0.1, 0.01, 0.001};
    private JComboBox precise = new JComboBox(items);
    private JLabel preciseChoiceLabel = new JLabel("Точность результата");

    private JPanel methodChoice = new JPanel();
    private JRadioButton leftMethodButton = new JRadioButton("левых прямоугольников");
    private JRadioButton midMethodButton = new JRadioButton("правых прямоугольников");
    private JRadioButton rightMethodButton = new JRadioButton("средних прямоугольников");
    private JLabel methodChoiceText = new JLabel("Считать методом");

    private JPanel funcChoice = new JPanel();
    private JRadioButton func1 = new JRadioButton("1/ln(x)");
    private JRadioButton func2 = new JRadioButton("x^2");
    private JRadioButton func3 = new JRadioButton("sqrt(x)");
    private JRadioButton func5 = new JRadioButton("sin(x) + 1");
    private JLabel funcChoiceText = new JLabel("Выберите функцию");

    private JPanel result = new JPanel();
    private JTextArea resultField = new JTextArea(15, 30);
    private JLabel resultLabel = new JLabel("Результат");

    private JButton magicButton = new JButton("Вычислить интеграл");

    private JPanel leftGroup = new JPanel();


    private RectangleIntegration.Method chosenMethod = RectangleIntegration.Method.LEFT; // 0 = left, 2 = mid, 3 = right
    private int chosenFunc = 0;


    IntegrationGUI() {

        super();
        this.setBackground(Color.GRAY);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));
        this.setPreferredSize(new Dimension(1200, 500));
        initComponents();
        placeComponents();
        setListeners();

    }

    private void initComponents() {
        methodChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        methodChoice.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        methodChoice.setPreferredSize(new Dimension(235, 150));
        methodChoice.add(methodChoiceText);
        methodChoice.add(leftMethodButton);
        methodChoice.add(midMethodButton);
        methodChoice.add(rightMethodButton);
        ButtonGroup methodGroup = new ButtonGroup();
        methodGroup.add(leftMethodButton);
        methodGroup.add(rightMethodButton);
        methodGroup.add(midMethodButton);
        leftMethodButton.setActionCommand("0");
        midMethodButton.setActionCommand("1");
        rightMethodButton.setActionCommand("2");

        funcChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        funcChoice.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        funcChoice.setPreferredSize(new Dimension(235, 150));
        funcChoice.add(funcChoiceText);
        funcChoice.add(func1);
        funcChoice.add(func2);
        funcChoice.add(func3);
        funcChoice.add(func5);
        ButtonGroup funcGroup = new ButtonGroup();
        funcGroup.add(func1);
        funcGroup.add(func2);
        funcGroup.add(func3);
        funcGroup.add(func5);
        func1.setActionCommand("0");
        func2.setActionCommand("1");
        func3.setActionCommand("2");
        func5.setActionCommand("4");

        result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        result.setPreferredSize(new Dimension(500, 400));
        result.add(resultLabel);
        result.add(resultField);
        resultField.setEditable(false);
        resultField.setFont(new Font("Verdana", Font.PLAIN, 16));

        boundsChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boundsChoice.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        boundsChoice.setPreferredSize(new Dimension(235, 150));
        lowerBound.setToolTipText("нижний");
        upperBound.setToolTipText("верхний");
        boundsChoice.add(boundChoiceLabel);
        boundsChoice.add(upperBound);
        boundsChoice.add(lowerBound);

        preciseChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        preciseChoice.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        preciseChoice.setPreferredSize(new Dimension(235, 150));
        preciseChoice.add(preciseChoiceLabel);
        precise.setFont(new Font("Verdana", Font.PLAIN, 15));
        preciseChoice.add(precise);

        leftGroup.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        leftGroup.setPreferredSize(new Dimension(600, 400));
        leftGroup.add(funcChoice);
        leftGroup.add(boundsChoice);
        leftGroup.add(preciseChoice);
        leftGroup.add(methodChoice);
        leftGroup.add(magicButton);

        desc.setFont(new Font("Verdana", Font.BOLD, 30));
    }

    private void placeComponents() {
        this.add(leftGroup);
        this.add(result);
        //this.add(desc);
    }

    private void setListeners() {
        ActionListener methodListener = e -> {
            if (e.getActionCommand().equals("0")) chosenMethod = RectangleIntegration.Method.LEFT;
            else if (e.getActionCommand().equals("1")) chosenMethod = RectangleIntegration.Method.MID;
            else chosenMethod = RectangleIntegration.Method.RIGHT;
        };
        leftMethodButton.addActionListener(methodListener);
        midMethodButton.addActionListener(methodListener);
        rightMethodButton.addActionListener(methodListener);

        ActionListener funcListener = e -> chosenFunc = Integer.parseInt(e.getActionCommand());
        func1.addActionListener(funcListener);
        func2.addActionListener(funcListener);
        func3.addActionListener(funcListener);
        func5.addActionListener(funcListener);

        ActionListener magic = e -> {
            //System.out.println(chosenFunc);
            Double res;

            try {
                Pair<Double, Double> bounds = new Pair<>(Double.parseDouble(lowerBound.getText()),
                        Double.parseDouble(upperBound.getText()));
                DoubleUnaryOperator fun;
                if (chosenFunc == 0) {
                    fun = (x) -> 1 / Math.log(x);
                } else if (chosenFunc == 1) {
                    fun = (x) -> x * x;
                } else if (chosenFunc == 2) {
                    fun = Math::sqrt;
                } else if (chosenFunc == 3) {
                    fun = (x) -> 1 / x;
                } else {
                    fun = (x) -> Math.sin(x) + 1;
                }
                double prec = Double.parseDouble(precise.getSelectedItem().toString());
                res = RectangleIntegration.runge(chosenMethod, fun, bounds, prec);
            } catch (Exception ex) {
                resultField.setText("Введите корректные данные");
                return;
            }
            if (res.isNaN() || res.isInfinite())  resultField.setText("Функция не определена на данной области");
            else {
                NumberFormat formatter;
                if (precise.getSelectedIndex() == 0)
                    formatter = new DecimalFormat("#0.0");
                else if (precise.getSelectedIndex() == 1)
                    formatter = new DecimalFormat("#0.00");
                else
                    formatter = new DecimalFormat("#0.000");
                String text = "Результаты вычислений:\n";
                text += "Интеграл: " + formatter.format(res) + "\n";
                text += "Погрешность: " + formatter.format(RectangleIntegration.rungeError) + "\n";
                text += "Разбиение на " + RectangleIntegration.rungeSteps + " частей";
                resultField.setText(text);
            }
        };
        magicButton.addActionListener(magic);

    }


}
