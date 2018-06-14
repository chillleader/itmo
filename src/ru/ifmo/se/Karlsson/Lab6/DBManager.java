package ru.ifmo.se.Karlsson.Lab6;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import ru.ifmo.se.Karlsson.Color;
import ru.ifmo.se.Karlsson.Human;
import ru.ifmo.se.Karlsson.Main;
import ru.ifmo.se.Karlsson.State;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class DBManager {

    public static final DBManager INSTANCE = new DBManager();

    private static Connection studsConnection;

    private static ArrayList<String> fields = new ArrayList<>();
    private static ArrayList<Class> types = new ArrayList<>();

    private DBManager() {
    }

    public static void createSSHTunnel() {
        String SSHHost = "se.ifmo.ru";
        String SSHUser = "s243854";
        String SSHPassword = "cuu001";
        int SSHPort = 2222;

        String remoteHost = "pg";
        int localPort = 5432;
        int remotePort = 5432;

        try {
            JSch jsch = new JSch();

            Session session = jsch.getSession(SSHUser, SSHHost, SSHPort);

            session.setPassword(SSHPassword);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            int assignedPort = session.setPortForwardingL(localPort, remoteHost, remotePort);

            System.out.println("localhost:" + assignedPort + " -> " + remoteHost + ":" + remotePort);
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    public void connect() throws SQLException {
        createSSHTunnel();

        try {
            Class.forName("org.postgresql.Driver");

            studsConnection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/studs",
                            "s243854", "cuu001");

            System.out.println("База данных подключена");
            Server.gui.dbName.setText("Helios/studs");


            Statement statement = studsConnection.createStatement();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void getFields() {
        fields = new ArrayList<>();
        types = new ArrayList<>();
        for (Field f : Human.class.getDeclaredFields()) {
            int mods = f.getModifiers();
            if (Modifier.isTransient(mods)) continue;
            fields.add(f.getName());
            types.add(f.getType());
        }
    }

    public void createTable(String tableName) throws SQLException {
        if (studsConnection == null || studsConnection.isClosed()) return;
        String sql = "CREATE TABLE ";
        sql += tableName;
        sql += " (";
        getFields();
        for (int i = 0; i < fields.size(); i++) {
            sql += fields.get(i);
            //if (types.get(i).equals(Integer.class)) sql += " integer";
            if (types.get(i).equals(double.class)) sql += " float8";
            else sql += " text";
            if (i < fields.size() - 1) sql += ", ";
            else sql += ")";
        }
        System.out.println(sql);
        Statement statement = studsConnection.createStatement();
        statement.executeUpdate(sql);
    }

    /**
     * Формирует SQL-запрост INSERT для указанной таблицы и указанного экземпляра Human
     *
     * @param tableName Имя таблицы, в которую производится вставка
     * @param r         Порядковый номер вставляемого элемента в таблице JTable
     * @return SQL INSERT
     * @throws SQLException Ошибка
     */
    public String insert(String tableName, int r) throws SQLException {
        getFields();
        String sql = "INSERT INTO ";
        sql += tableName;
        sql += " VALUES (";
        for (int i = 0; i < fields.size(); i++) {
            if (!types.get(i).equals(double.class)) sql += "\'";
            sql += Server.gui.tm.getValueAt(r, i);
            if (!types.get(i).equals(double.class)) sql += "\'";
            if (i < fields.size() - 1) sql += ", ";
            else sql += ")";
        }
        System.out.println(sql);
        return sql;
    }


    public void save(String sql) throws SQLException {
        Statement s = studsConnection.createStatement();
        s.executeUpdate(sql);
    }

    public void read(String table) throws SQLException {
        Statement s = studsConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "SELECT * FROM ";
        sql += table;
        ResultSet result = s.executeQuery(sql);
        getFields();
        result.beforeFirst();
        PriorityBlockingQueue<Human> q = new PriorityBlockingQueue<>();
        while (result.next()) {
            Human h = new Human();
            double d = 0;
            String str = "";
            for (int i = 0; i < fields.size(); i++) {
                if (types.get(i).equals(double.class)) {
                    d = result.getDouble(i + 1);
                } else {
                    str = result.getString(i + 1);
                }

                if (i == 0) h.setName(str);
                if (i == 1) h.setMale(str.equals("мужской"));
                if (i == 2) h.setSize(d);
                if (i == 3) h.setColor(Color.valueOf(str));
                if (i == 4) h.setX(d);
                if (i == 5) h.setY(d);
                if (i == 6) h.setDateTimeString(str);
            }
            q.add(h);
        }
        CollectionManager.q = q;
        Server.gui.tm.fireTableDataChanged();
    }
}
