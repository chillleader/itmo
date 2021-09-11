package ru.ifmo.se.Karlsson.Lab6;


import org.intellij.lang.annotations.Flow;
import ru.ifmo.se.Karlsson.Human;
import ru.ifmo.se.Karlsson.Main;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;

import static java.awt.Color.*;
import static ru.ifmo.se.Karlsson.Lab6.ClientGUI.*;
import static ru.ifmo.se.Karlsson.Lab6.ClientGUI.start;

public class ClientGUI extends JFrame {

    final static int WIDTH = 500;
    final static int HEIGHT = 500;

    static String nameConstr = "";
    static int sizeConstr = 200;
    static int sexConstr = 0;
    static java.util.List<String> colorConstr = Arrays.asList(ru.ifmo.se.Karlsson.Color.getStrings());

    static boolean askForRefresh = true;

    static Container pane;
    static JLabel connected = new JLabel("Подключение отсутсвует");

    static PriorityBlockingQueue<Human> q;

    static JComboBox<String> sex = new JComboBox<>(new String[]{"", "мужской", "женский"});
    static JList<String> colors = new JList<>(ru.ifmo.se.Karlsson.Color.getStrings());
    static JSlider size = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
    static JTextField nameFilter = new JTextField();
    public static JButton start = new JButton("Старт");

    static ArrayList<Circle> circles = new ArrayList<>();

    static JPanel canvas = new JPanel();

    static int canvasX, canvasY;

    static AnimationManager am;

    static JButton refresh;
    static JLabel filterLabel = new JLabel(), sexLabel = new JLabel(), nameLabel = new JLabel();
    static JLabel colorLabel = new JLabel(), sizeLabel = new JLabel();
    static String startText, stopText, filterText, nameText, sexText, colorText, sizeText;

    static JLabel dataLabel = new JLabel();

    static Locale serb = new Locale("sr", "RS");
    static Locale bulg = new Locale("bg", "BG");
    static Locale span = new Locale("es", "MX");
    static Locale russ = new Locale("ru", "RU");
    static Locale curLocale = russ;

    public ClientGUI() {
        super("Client");
        pane = this.getContentPane();
        this.setBounds(0, 0, 1250, 700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 40, 50));

        canvas.setPreferredSize(new Dimension(900, 500));
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        canvas.setLayout(null);
        drawCircles();

        pane.add(canvas);
        am = new AnimationManager(Arrays.asList(canvas));
        JPanel filters = new JPanel();
        filters.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 20));
        JLabel sign = filterLabel;
        sign.setPreferredSize(new Dimension(180, 25));
        Font font = new Font("Courier", Font.ITALIC, 12);
        filters.add(sign);
        filters.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        filters.setPreferredSize(new Dimension(200, 500));
        filters.add(nameLabel);

        nameFilter.setPreferredSize(new Dimension(150, 30));
        nameFilter.getDocument().addDocumentListener(new NameFilterListener());
        filters.add(nameFilter);
        filters.add(sexLabel);

        sex.setPreferredSize(new Dimension(150, 30));
        sex.addActionListener(new SexFilterListener());
        filters.add(sex);
        filters.add(colorLabel);

        colors.setPreferredSize(new Dimension(145, 130));
        colors.addListSelectionListener(new ColorFilterListener());
        filters.add(colors);

        filters.add(sizeLabel);
        size.setMajorTickSpacing(50);
        size.setMinorTickSpacing(10);
        size.setPaintLabels(true);
        size.setPaintTicks(true);
        //size.setPaintTrack(true);
        size.setPreferredSize(new Dimension(180, 40));
        size.addChangeListener(new SizeFilterListener());
        filters.add(size);

        pane.add(filters);
        refresh = new JButton("Обновить");
        pane.add(connected);
        refresh.addActionListener(new RefreshActionListener());
        pane.add(refresh);
        start.addActionListener(new StartListener());
        pane.add(start);
        dataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateLocale(russ);

        JComboBox<String> lang = new JComboBox<>(new String[] {"Русский", "Српски", "Български", "Español"});
        lang.addActionListener(new LangListener());
        pane.add(lang);

        pane.add(dataLabel);
        pane.repaint();
    }

    public void drawCircles() {

        //canvas = new JPanel();
        canvas.removeAll();
        canvas.setPreferredSize(new Dimension(900, 500));
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        circles = new ArrayList<>();

        if (q == null) return;
        for (Human h : q) {
            Circle c = new Circle(
                    h.getSize(),
                    h.getColor(),
                    h.getName(),
                    h.isMale(),
                    h
            );
            c.setBounds(
                    (int) h.getX() * canvas.getWidth() / 100,
                    (int) h.getY() * canvas.getHeight() / 100,
                    (int) (h.getSize() / 1.3),
                    (int) (h.getSize() / 1.3));
            c.setToolTipText(h.getName());
            c.addMouseListener(new ObjectClickListener());
            circles.add(c);
            canvas.add(c);
        }
    }

    public static void enableCircles() {
        for (Circle c:circles) {
            c.setVisible(c.sexOk && c.colorOk && c.nameOk && c.sizeOk);
        }
    }

    public static void checkCircles() {
        for (Circle c : circles) c.colorOk = false;
        for (String s : colorConstr) {
            for (Circle c : circles) {
                Color col = c.color;
                if (col.equals(Color.RED) && s.equals("red")) c.colorOk = true;
                else if (col.equals(Color.BLACK) && s.equals("black")) c.colorOk = true;
                else if (col.equals(Color.ORANGE) && s.equals("orange")) c.colorOk = true;
                else if (col.equals(Color.GREEN) && s.equals("green")) c.colorOk = true;
                else if (col.equals(Color.YELLOW) && s.equals("yellow")) c.colorOk = true;
                else if (col.equals(Color.BLUE) && s.equals("blue")) c.colorOk = true;
                else if (col.equals(Color.MAGENTA) && s.equals("purple")) c.colorOk = true;
            }
        }
        for (Circle c : circles) {
            if ((int) c.d > sizeConstr) c.sizeOk = false;
            else c.sizeOk = true;

            if (sexConstr == 1 && !c.isMale || sexConstr == 2 && c.isMale) c.sexOk = false;
            else c.sexOk = true;

            if (!c.name.contains(nameConstr)) c.nameOk = false;
            else c.nameOk = true;
        }
        enableCircles();
    }

    public static void updateLocale(Locale l) {

        ResourceBundle rb = PropertyResourceBundle.getBundle("resources", l);
        refresh.setText(rb.getString("update"));
        connected.setText(rb.getString("disconnected"));
        startText = rb.getString("start");
        stopText = rb.getString("stop");
        start.setText(((StartListener)(start.getActionListeners()[0])).strt ? startText : stopText);
        filterLabel.setText(rb.getString("filterText"));
        sexLabel.setText(rb.getString("sexText"));
        colorLabel.setText(rb.getString("colorText"));
        nameLabel.setText(rb.getString("nameText"));
        sizeLabel.setText(rb.getString("sizeText"));
    }

}

class Circle extends JPanel {

    double d;
    Color color;
    String name;
    boolean isMale;
    Human h;
    boolean nameOk = true, sexOk = true, colorOk = true, sizeOk = true;

    Circle(double d, ru.ifmo.se.Karlsson.Color clr, String name, boolean isMale, Human h) {
        super();
        this.d = (int) d;
        switch (clr) {
            case yellow:
                color = Color.YELLOW;
                break;
            case green:
                color = Color.GREEN;
                break;
            case red:
                color = RED;
                break;
            case purple:
                color = Color.MAGENTA;
                break;
            case black:
                color = Color.BLACK;
                break;
            case blue:
                color = BLUE;
                break;
            case orange:
                color = Color.ORANGE;
                break;
            default:
                color = Color.BLACK;
                break;
        }
        this.name = name;
        this.isMale = isMale;
        this.h = h;
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(color);
        g.drawOval(0, 0, (int) d / 2, (int) d / 2);
        g.fillOval(0, 0, (int) d / 2, (int) d / 2);
    }
}

class RefreshActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ClientGUI.askForRefresh = true;
    }
}

class NameFilterListener implements DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            String s = e.getDocument().getText(0, e.getDocument().getLength()).trim();
            nameConstr = s;
            checkCircles();
            ClientGUI.enableCircles();
            Main.gui.repaint();
        } catch (BadLocationException ex) { }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        insertUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}

class SexFilterListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        int res = ((JComboBox<String>) e.getSource()).getSelectedIndex();
        sexConstr = res;
        checkCircles();
        ClientGUI.enableCircles();
        Main.gui.repaint();
    }
}

class ColorFilterListener implements ListSelectionListener {

    @Override
    public void valueChanged(ListSelectionEvent e) {
        List<String> strings = ((JList<String>) e.getSource()).getSelectedValuesList();
        colorConstr = strings;
        for (Circle c : circles) c.colorOk = false;
        checkCircles();
        ClientGUI.enableCircles();
        Main.gui.repaint();
    }
}

class SizeFilterListener implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent e) {
        if (!((JSlider)e.getSource()).getValueIsAdjusting()) {
            sizeConstr = ((JSlider) e.getSource()).getValue();
            checkCircles();
            ClientGUI.enableCircles();
            Main.gui.repaint();
        }
    }
}

class StartListener implements ActionListener {

    boolean strt = true;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (strt) {
            am = new AnimationManager(circles);
            am.startWithFixedDelay(0, 400, 5, 4, 1, TimeUnit.MILLISECONDS);
            am.startWithFixedDelay(2000, 500, 10, 2, -1, TimeUnit.MILLISECONDS);
            start.setText(stopText);
            strt = !strt;
        }
        else {
            am.stopAnimation();
            start.setText(startText);
            strt = !strt;
        }
    }
}

class LangListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        int res = ((JComboBox<String>) e.getSource()).getSelectedIndex();
        switch (res) {
            case 0: updateLocale(russ); break;
            case 1: updateLocale(serb); break;
            case 2: updateLocale(bulg); break;
            case 3: updateLocale(span); break;
            default: updateLocale(russ); break;
        }
    }
}

class ObjectClickListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        Circle c = (Circle)e.getSource();
        NumberFormat formatter = NumberFormat.getInstance(curLocale);
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(curLocale);
        String s = c.name + ", size: ";
        s += c.d;
        s += ", X: ";
        s += formatter.format(c.h.getX());
        s += " Y: ";
        s += formatter.format(c.h.getY());
        s += ", created: ";
        c.h.updateDateTime();
        s += c.h.dateTime.format(dtf);
        dataLabel.setText(s);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
