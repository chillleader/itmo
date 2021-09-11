package ru.ifmo.se.Karlsson.Lab6;

import jdk.nashorn.internal.scripts.JO;
import org.intellij.lang.annotations.Flow;
import ru.ifmo.se.Karlsson.Human;
import ru.ifmo.se.Karlsson.Karlsson;
import ru.ifmo.se.Karlsson.Lab5.FileIO;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.BadLocationException;

public class ServerGUI extends JFrame {

    JTable table;
    Container pane;
    JButton button = new JButton("Загрузить");
    JButton button1 = new JButton("Сохранить");
    JButton editButton = new JButton("Редактировать");
    JButton addButton = new JButton("Добавить");
    JButton removeButton = new JButton("Удалить");
    JScrollPane scrlp;
    TableModel tm = new TableModel();
    JButton add_if_min = new JButton("ADD IF MIN");
    JButton add_if_max = new JButton("ADD IF MAX");
    JButton remove_lower = new JButton("REMOVE LOWER");
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("Коллекция");
    JMenu dbMenu = new JMenu("База данных");
    JMenuItem dbName = new JMenuItem("Не подключено");
    JMenuItem dbConnectButton = new JMenuItem("Переподключиться");
    JMenuItem dbCreate = new JMenuItem("Создать таблицу");
    JMenuItem dbSaveAll = new JMenuItem("Выгрузить в БД");
    JMenuItem dbSaveOne = new JMenuItem("Загрузить выбранный элемент в БД");
    JMenuItem dbLoad = new JMenuItem("Загрузить из таблицы");
    JMenuItem load = new JMenuItem("Загрузить");
    JMenuItem save = new JMenuItem("Сохранить");

    boolean isObjSelected = false;

    java.util.List<String> createdTables = new ArrayList<>();

    public ServerGUI() {
        super("Server");
        this.setBounds(0, 0, 750, 400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        table = new JTable(tm);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrlp = new JScrollPane(table);
        scrlp.setPreferredSize(new Dimension(710, 200));
        scrlp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane = this.getContentPane();

        load.addActionListener(new LoadActionListener());
        save.addActionListener(new SaveActionListener());
        fileMenu.add(load);
        fileMenu.add(save);
        menuBar.add(fileMenu);
        dbName.addActionListener(new DbConnectListener());
        dbMenu.add(dbName);
        dbCreate.addActionListener(new DbCreateListener());
        dbMenu.add(dbCreate);
        dbSaveAll.addActionListener(new DbSaveAllListener());
        dbSaveOne.addActionListener(new DbSaveOneListener());
        dbLoad.addActionListener(new DbReadListener());
        dbMenu.add(dbSaveAll);
        dbMenu.add(dbSaveOne);
        dbMenu.add(dbLoad);
        menuBar.add(dbMenu);
        dbCreate.setEnabled(false);
        dbSaveOne.setEnabled(false);
        dbSaveAll.setEnabled(false);
        dbLoad.setEnabled(false);
        this.setJMenuBar(menuBar);

        addButton.setPreferredSize(new Dimension(130, 25));
        editButton.setPreferredSize(new Dimension(130, 25));
        removeButton.setPreferredSize(new Dimension(130, 25));
        Container controls = new Container();
        controls.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pane.add(scrlp);
        controls.add(addButton);
        controls.add(editButton);
        controls.add(removeButton);
        controls.add(add_if_min);
        controls.add(add_if_max);
        controls.add(remove_lower);
        controls.setPreferredSize(new Dimension(500, 200));
        editButton.setEnabled(false);
        removeButton.setEnabled(false);
        removeButton.addActionListener(new RemoveActionListener());
        addButton.addActionListener(new AddActionListener());
        editButton.addActionListener(new ChangeActionListener());
        add_if_min.addActionListener(new MinActionListener());
        add_if_max.addActionListener(new MaxActionListener());
        remove_lower.addActionListener(new RLActionListener());
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                editButton.setEnabled(true);
                removeButton.setEnabled(true);
            }
        });
        pane.add(controls);
    }

    class TableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return CollectionManager.q.size();
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public String getColumnName(int c) {
            String result = "";
            switch (c) {
                case 0:
                    result = "Имя";
                    break;
                case 1:
                    result = "Пол";
                    break;
                case 2:
                    result = "Размер";
                    break;
                case 3:
                    result = "Цвет";
                    break;
                case 4:
                    result = "X";
                    break;
                case 5:
                    result = "Y";
                    break;
                case 6:
                    result = "Когда создан";
                    break;
            }
            return result;
        }

        @Override
        public Object getValueAt(int r, int c) {
            Object[] humans = CollectionManager.q.toArray();
            switch (c) {
                case 0:
                    return ((Human) humans[r]).getName();
                case 1:
                    return ((Human) humans[r]).isMale() ? "мужской" : "женский";
                case 2:
                    return ((Human) humans[r]).getSize();
                case 3:
                    return ((Human) humans[r]).getColor().toString();
                case 4:
                    return ((Human) humans[r]).getX();
                case 5:
                    return ((Human) humans[r]).getY();
                case 6:
                    return ((Human) humans[r]).getDateTimeString();
                default:
                    return "";
            }
        }
    }

    class LoadActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String s;
                s = (String) JOptionPane.showInputDialog(
                        null,
                        "Введите имя файла или полный путь:",
                        "Загрузка",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        CollectionManager.file);
                FileIO f = new FileIO();
                CollectionManager.q = f.load(s);
                tm.fireTableDataChanged();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Файл не найден",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException ex) {
            }
        }
    }

    class SaveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String s;
            s = (String) JOptionPane.showInputDialog(
                    null,
                    "Введите имя файла или полный путь:",
                    "Сохранение",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    CollectionManager.file);
            if (s == null) return;
            FileIO f = new FileIO();
            CollectionManager.file = s;
            f.save();
        }
    }

    class RemoveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            CollectionManager.q.remove((Human) (CollectionManager.q.toArray())[table.getSelectedRow()]);
            tm.fireTableDataChanged();
        }
    }

    class AddActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField name = new JTextField();
            JComboBox sex = new JComboBox(new String[]{"мужской", "женский"});
            JComboBox color = new JComboBox(ru.ifmo.se.Karlsson.Color.getStrings());
            JFormattedTextField size = new JFormattedTextField(NumberFormat.getNumberInstance());
            JFormattedTextField x = new JFormattedTextField(NumberFormat.getNumberInstance());
            JFormattedTextField y = new JFormattedTextField(NumberFormat.getNumberInstance());
            JComponent[] inputs = new JComponent[]{
                    new JLabel("Имя"),
                    name,
                    new JLabel("Пол"),
                    sex,
                    new JLabel("Цвет"),
                    color,
                    new JLabel("Размер"),
                    size,
                    new JLabel("X"),
                    x,
                    new JLabel("Y"),
                    y
            };
            int result = JOptionPane.showConfirmDialog(null, inputs, "Добавление героя",
                    JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                Human h = new Human();
                h.setName(name.getText());
                h.setMale(((String) sex.getSelectedItem()).equals("мужской"));
                h.setSize(Double.parseDouble(size.getText()));
                h.setColor(ru.ifmo.se.Karlsson.Color.valueOf((String) color.getSelectedItem()));
                h.setX(Double.parseDouble(x.getText()));
                h.setY(Double.parseDouble(y.getText()));
                CollectionManager.q.add(h);
                tm.fireTableDataChanged();
            }
        }
    }

    class ChangeActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Human h = (Human) (CollectionManager.q.toArray())[table.getSelectedRow()];
            JTextField name = new JTextField();
            name.setText(h.getName());
            JComboBox sex = new JComboBox(new String[]{"мужской", "женский"});
            sex.setSelectedIndex(h.isMale() ? 0 : 1);
            JComboBox color = new JComboBox(ru.ifmo.se.Karlsson.Color.getStrings());
            color.setSelectedItem(h.getColor());
            JFormattedTextField size = new JFormattedTextField(NumberFormat.getNumberInstance());
            size.setText(String.valueOf(h.getSize()));
            JFormattedTextField x = new JFormattedTextField(NumberFormat.getNumberInstance());
            x.setText(String.valueOf(h.getX()));
            JFormattedTextField y = new JFormattedTextField(NumberFormat.getNumberInstance());
            y.setText(String.valueOf(h.getY()));
            JComponent[] inputs = new JComponent[]{
                    new JLabel("Имя"),
                    name,
                    new JLabel("Пол"),
                    sex,
                    new JLabel("Цвет"),
                    color,
                    new JLabel("Размер"),
                    size,
                    new JLabel("X"),
                    x,
                    new JLabel("Y"),
                    y
            };
            int result = JOptionPane.showConfirmDialog(null, inputs, "Изменение героя",
                    JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                h.setName(name.getText());
                h.setMale(((String) sex.getSelectedItem()).equals("мужской"));
                h.setSize(Double.parseDouble(size.getText()));
                h.setColor(ru.ifmo.se.Karlsson.Color.valueOf((String) color.getSelectedItem()));
                h.setX(Double.parseDouble(x.getText()));
                h.setY(Double.parseDouble(y.getText()));
                tm.fireTableDataChanged();
            }
        }
    }

    class MinActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField name = new JTextField();
            JComboBox sex = new JComboBox(new String[]{"мужской", "женский"});
            JComboBox color = new JComboBox(ru.ifmo.se.Karlsson.Color.getStrings());
            JFormattedTextField size = new JFormattedTextField(NumberFormat.getNumberInstance());
            JFormattedTextField x = new JFormattedTextField(NumberFormat.getNumberInstance());
            JFormattedTextField y = new JFormattedTextField(NumberFormat.getNumberInstance());
            JComponent[] inputs = new JComponent[]{
                    new JLabel("Имя"),
                    name,
                    new JLabel("Пол"),
                    sex,
                    new JLabel("Цвет"),
                    color,
                    new JLabel("Размер"),
                    size,
                    new JLabel("X"),
                    x,
                    new JLabel("Y"),
                    y
            };
            int result = JOptionPane.showConfirmDialog(null, inputs, "ADD IF MIN",
                    JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                Human h = new Human();
                h.setName(name.getText());
                h.setMale(((String) sex.getSelectedItem()).equals("мужской"));
                h.setSize(Double.parseDouble(size.getText()));
                h.setColor(ru.ifmo.se.Karlsson.Color.valueOf((String) color.getSelectedItem()));
                h.setX(Double.parseDouble(x.getText()));
                h.setY(Double.parseDouble(y.getText()));
                CollectionManager.INSTANCE.add_if_min(h);
                tm.fireTableDataChanged();
            }
        }
    }

    class MaxActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField name = new JTextField();
            JComboBox sex = new JComboBox(new String[]{"мужской", "женский"});
            JComboBox color = new JComboBox(ru.ifmo.se.Karlsson.Color.getStrings());
            JFormattedTextField size = new JFormattedTextField(NumberFormat.getNumberInstance());
            JFormattedTextField x = new JFormattedTextField(NumberFormat.getNumberInstance());
            JFormattedTextField y = new JFormattedTextField(NumberFormat.getNumberInstance());
            JComponent[] inputs = new JComponent[]{
                    new JLabel("Имя"),
                    name,
                    new JLabel("Пол"),
                    sex,
                    new JLabel("Цвет"),
                    color,
                    new JLabel("Размер"),
                    size,
                    new JLabel("X"),
                    x,
                    new JLabel("Y"),
                    y
            };
            int result = JOptionPane.showConfirmDialog(null, inputs, "ADD IF MAX",
                    JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                Human h = new Human();
                h.setName(name.getText());
                h.setMale(((String) sex.getSelectedItem()).equals("мужской"));
                h.setSize(Double.parseDouble(size.getText()));
                h.setColor(ru.ifmo.se.Karlsson.Color.valueOf((String) color.getSelectedItem()));
                h.setX(Double.parseDouble(x.getText()));
                h.setY(Double.parseDouble(y.getText()));
                CollectionManager.INSTANCE.add_if_max(h);
                tm.fireTableDataChanged();
            }
        }
    }

    class RLActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField name = new JTextField();
            JComboBox sex = new JComboBox(new String[]{"мужской", "женский"});
            JComboBox color = new JComboBox(ru.ifmo.se.Karlsson.Color.getStrings());
            JFormattedTextField size = new JFormattedTextField(NumberFormat.getNumberInstance());
            JFormattedTextField x = new JFormattedTextField(NumberFormat.getNumberInstance());
            JFormattedTextField y = new JFormattedTextField(NumberFormat.getNumberInstance());
            JComponent[] inputs = new JComponent[]{
                    new JLabel("Имя"),
                    name,
                    new JLabel("Пол"),
                    sex,
                    new JLabel("Цвет"),
                    color,
                    new JLabel("Размер"),
                    size,
                    new JLabel("X"),
                    x,
                    new JLabel("Y"),
                    y
            };
            int result = JOptionPane.showConfirmDialog(null, inputs, "REMOVE LOWER",
                    JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                Human h = new Human();
                h.setName(name.getText());
                h.setMale(((String) sex.getSelectedItem()).equals("мужской"));
                h.setSize(Double.parseDouble(size.getText()));
                h.setColor(ru.ifmo.se.Karlsson.Color.valueOf((String) color.getSelectedItem()));
                h.setX(Double.parseDouble(x.getText()));
                h.setY(Double.parseDouble(y.getText()));
                CollectionManager.INSTANCE.remove_lower(h);
                tm.fireTableDataChanged();
            }
        }
    }

    class DbConnectListener implements ActionListener {

        boolean isConnected = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isConnected) return;
            try {
                DBManager.INSTANCE.connect();
                isConnected = true;
                dbCreate.setEnabled(true);
                dbLoad.setEnabled(true);
                dbSaveAll.setEnabled(true);
                dbSaveOne.setEnabled(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Не удалось соединиться с БД", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    class DbCreateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = JOptionPane.showInputDialog("Введите имя для таблицы");
            try {
                if (s.equals("")) return;
                DBManager.INSTANCE.createTable(s);
                Server.gui.createdTables.add(s);
                JOptionPane.showMessageDialog(null, "Таблица сохранена", "Успех",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Неправильное имя таблицы", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (NullPointerException ex) {}
        }
    }

    class DbSaveAllListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            JComponent[] inputs = new JComponent[]{
                    new JLabel("Выберите таблицу:"),
                    new JTextField("Введите название..."),
                    new JLabel("или выберите из созданных"),
                    new JComboBox<>(Server.gui.createdTables.toArray()),
            };
            int result = JOptionPane.showConfirmDialog(null, inputs, "Сохранение в БД",
                    JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {

                try {
                    for (int i = 0; i < table.getRowCount(); i++) {
                        if (inputs[1] == null || ((JTextField) inputs[1]).getDocument().getText(0,
                                ((JTextField) inputs[1]).getDocument().getLength()).equals("")) {


                            DBManager.INSTANCE.save(DBManager.INSTANCE.insert(
                                    (String) (((JComboBox) inputs[3]).getSelectedItem()),
                                    i
                            ));
                        } else {
                            DBManager.INSTANCE.save(DBManager.INSTANCE.insert(
                                    ((JTextField) inputs[1]).getDocument().getText(0, ((JTextField) inputs[1]).getDocument().getLength()),
                                    i
                            ));
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Успешно");
                } catch (SQLException ex) {
                    System.err.print(ex);
                    JOptionPane.showMessageDialog(null, "Таблица не существует");
                } catch (BadLocationException ex) {
                    System.err.print(ex);
                }
            }

        }
    }

    class DbSaveOneListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int r = table.getSelectedRow();

            if (r == -1) {
                JOptionPane.showMessageDialog(null, "Элемент в таблице не выбран");
                return;
            }
            JComponent[] inputs = new JComponent[]{
                    new JLabel("Выберите таблицу"),
                    new JTextField("Введите название..."),
                    new JLabel("или выберите из созданных"),
                    new JComboBox<>(Server.gui.createdTables.toArray()),
            };
            int result = JOptionPane.showConfirmDialog(null, inputs, "Сохранение в БД",
                    JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {

                try {
                    if (inputs[1] == null || ((JTextField) inputs[1]).getDocument().getText(0,
                            ((JTextField) inputs[1]).getDocument().getLength()).equals("")) {


                        DBManager.INSTANCE.save(DBManager.INSTANCE.insert(
                                (String) (((JComboBox) inputs[3]).getSelectedItem()),
                                r
                        ));
                    } else {
                        DBManager.INSTANCE.save(DBManager.INSTANCE.insert(
                                ((JTextField) inputs[1]).getDocument().getText(0, ((JTextField) inputs[1]).getDocument().getLength()),
                                r
                        ));
                    }
                    JOptionPane.showMessageDialog(null, "Успешно");
                } catch (SQLException ex) {
                    System.err.print(ex);
                    JOptionPane.showMessageDialog(null, "Таблица не существует");
                } catch (BadLocationException ex) {
                    System.err.print(ex);
                }
            }
        }
    }

    class DbReadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComponent[] inputs = new JComponent[]{
                    new JLabel("Выберите таблицу:"),
                    new JTextField("Введите название..."),
                    new JLabel("или выберите из созданных"),
                    new JComboBox<>(Server.gui.createdTables.toArray()),
            };
            int result = JOptionPane.showConfirmDialog(null, inputs, "Загрузка из БД",
                    JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {

                try {
                    if (inputs[1] == null || ((JTextField) inputs[1]).getDocument().getText(0,
                            ((JTextField) inputs[1]).getDocument().getLength()).equals("")) {
                        DBManager.INSTANCE.read((String) (((JComboBox) inputs[3]).getSelectedItem()));
                    } else {
                        DBManager.INSTANCE.read(
                                ((JTextField) inputs[1]).getDocument().getText(0, ((JTextField) inputs[1]).getDocument().getLength())
                        );
                    }
                    JOptionPane.showMessageDialog(null, "Успешно");
                } catch (SQLException ex) {
                    System.err.print(ex);
                    JOptionPane.showMessageDialog(null, "Таблица не существует");
                } catch (BadLocationException ex) {
                    System.err.print(ex);
                }
            }
        }
    }
}