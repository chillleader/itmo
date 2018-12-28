package gui;

import Maths.Gaussian;
import Maths.Matrix;
import input.FileIn;
import input.MatrixParser;
import input.RandMatrix;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class MainGui extends JFrame {

    JMenuBar topMenu = new JMenuBar();
    JTabbedPane tabs = new JTabbedPane();
    Font font = new Font("Verdana", Font.PLAIN, 15);

    JTextArea input = new JTextArea(20, 40);
    JButton enter = new JButton("Считать");
    JTextArea output = new JTextArea(20, 40);

    JMenu file = new JMenu("Меню");
    JMenuItem open = new JMenuItem("Открыть файл");
    JMenuItem rand = new JMenuItem("Случайные");
    JMenuItem exit = new JMenuItem("Выход");

    private JMenu createFileMenu() {

        file.add(open);
        file.add(rand);
        file.addSeparator();
        file.add(exit);
        file.setFont(font);
        return file;
    }

    private void createTabs() {
        UIManager.put("Label.font", new Font("Verdana", Font.PLAIN, 15));
        UIManager.put("TextField.font", new Font("Verdana", Font.PLAIN, 15));
        UIManager.put("RadioButton.font", new Font("Verdana", Font.PLAIN, 15));
        tabs.addTab("ЛР №1", setLab());
        tabs.addTab("ЛР №2", new IntegrationGUI());
        UIManager.put("TextField.font", new Font("Verdana", Font.PLAIN, 20));
        UIManager.put("Button.font", new Font("Verdana", Font.PLAIN, 15));
        tabs.addTab("ЛР №3", new InterpolationGUI());
        tabs.addTab("ЛР №4", new CauchyProblemGUI());
        tabs.setFont(font);
        tabs.setSelectedIndex(2);
    }

    private JPanel setLab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        input.setEditable(true);
        input.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane scrollPane = new JScrollPane(input);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));
        input.setFont(font);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel inputContent = new JPanel();
        inputContent.setPreferredSize(new Dimension(630, 500));
        inputContent.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel label = new JLabel("Введите матрицу СЛАУ, разделитель - пробел");
        label.setBorder(new EmptyBorder(10, 10, 10, 10));
        label.setFont(font);
        inputContent.add(label);
        inputContent.add(scrollPane);
        enter.setSize(new Dimension(100, 50));
        inputContent.add(enter);
        inputContent.setPreferredSize(new Dimension(630, 500));

        JPanel outputContent = new JPanel();
        outputContent.setPreferredSize(new Dimension(630, 500));
        output.setEditable(false);
        output.setFont(font);
        output.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane scrollPaneOut = new JScrollPane(output);
        scrollPaneOut.setBorder(new EmptyBorder(0, 10, 10, 10));
        JLabel labelOut = new JLabel("Решение системы");
        labelOut.setBorder(new EmptyBorder(10, 10, 10, 10));
        labelOut.setFont(font);
        outputContent.setBorder(new EmptyBorder(10, 10, 10, 10));
        outputContent.add(labelOut);
        outputContent.add(scrollPaneOut);

        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(20, 20));
        panel.add(empty, BorderLayout.PAGE_END);

        panel.add(inputContent, BorderLayout.LINE_START);
        panel.add(outputContent, BorderLayout.LINE_END);
        return panel;
    }

    public MainGui() {
        super("Computational Mathematics");
        this.setBounds(100, 100, 1500, 900);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        topMenu.add(createFileMenu());
        setJMenuBar(topMenu);
        add(tabs, BorderLayout.NORTH);
        createTabs();
        setListeners();
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void setListeners() {
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DecimalFormat dec = new DecimalFormat("#0.00");
                Matrix m = null;
                try {
                    m = MatrixParser.INSTANCE.parse(input.getText());
                }
                catch (Exception ex) {
                    output.setText("Некорректный ввод");
                    return;
                }
                Matrix _m = new Matrix(m.HEIGHT, m.WIDTH, m.matrix);
                double det = Gaussian.eliminate(m);
                StringBuilder sb = new StringBuilder();

                try {
                    ArrayList<Double> solution = Gaussian.solve(m);
                    int c = 1;
                    for (Double d : solution) {
                        if (d == Double.NEGATIVE_INFINITY || d == Double.POSITIVE_INFINITY || Double.isNaN(d))
                            throw new IllegalArgumentException("Нет решений/бесконечно много решений");
                    }
                    sb.append("Определитель: ");
                    sb.append(det + "\n");
                    sb.append("Ступенчатая матрица:\n");
                    for (int i = 0; i < m.HEIGHT; i++) {
                        for (int j = 0; j < m.WIDTH; j++) {
                            sb.append(dec.format(m.at(i, j)));
                            sb.append(" ");
                        }
                        sb.append("\n");
                    }
                    for (Double d : solution) {
                        sb.append("X" + c + " = ");
                        sb.append(d);
                        sb.append("\n");
                        c++;
                    }
                    ArrayList<Double> disc = _m.getDiscrepancies(solution);
                    sb.append("Невязки:\n");
                    c = 1;
                    for (Double d : disc) {
                        sb.append(c + ") ");
                        sb.append(d);
                        sb.append("\n");
                        c++;
                    }
                }
                catch (IllegalArgumentException ex) {
                    sb.append(ex.getMessage());
                } catch (Exception ex) {
                    sb.append("Некорректный ввод");
                }
                finally {
                    output.setText(sb.toString());
                }
            }
        });

        rand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = Integer.parseInt(JOptionPane.showInputDialog("Введите размер матрицы"));
                input.setText(RandMatrix.generate(size));
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog("Введите путь к файлу");
                if (s == null) return;
                input.setText(FileIn.read(s));
            }
        });
    }

}
