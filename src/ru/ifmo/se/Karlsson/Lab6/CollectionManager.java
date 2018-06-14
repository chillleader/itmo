package ru.ifmo.se.Karlsson.Lab6;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ru.ifmo.se.Karlsson.Human;
import ru.ifmo.se.Karlsson.Lab5.Commands;
import ru.ifmo.se.Karlsson.Lab5.FileIO;
import ru.ifmo.se.Karlsson.Main;
import sun.misc.Queue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Predicate;

public class CollectionManager {

    public static PriorityBlockingQueue<Human> q = new PriorityBlockingQueue<>();
    public static String file = "kek.xml";

    public static final CollectionManager INSTANCE = new CollectionManager();

    private CollectionManager () {
        FileIO f = new FileIO();

        System.out.println("Загрузка состояния из " + file);
        try {
            q.addAll(f.load(file));
        } catch (Exception e) {

        }
    }

    public String add_if_min(Human h) {
        String response;
        Predicate<Human> p = e -> e.compareTo(h) < 0;
        if (q.parallelStream().anyMatch(p)) {
            response = "Условие не выполняется, элемент не добавлен.";
        }
        else {
            q.add(h);
            response = "Добавлен объект: " + Commands.lastArg;
        }
        Server.gui.tm.fireTableDataChanged();
        return response;
    }

    public String add_if_max(Human h) {
        String response;
        Predicate<Human> p = e -> e.compareTo(h) > 0;
        if (q.parallelStream().anyMatch(p)) {
            response = "Условие не выполняется, элемент не добавлен.";
        }
        else {
            q.add(h);
            response = "Добавлен объект: " + Commands.lastArg;
        }
        Server.gui.tm.fireTableDataChanged();
        return response;
    }

    public String remove_lower(Human h) {
        StringBuilder response = new StringBuilder();
        int count = 0;
        Predicate<Human> p = e -> e.compareTo(h) < 0;
        while (q.parallelStream().anyMatch(p)) {
            response.append(q.peek().getName());
            response.append(" удаляется");
            q.remove();
            count++;
        }
        response.append("По запросу удалено ");
        response.append(count);
        response.append(" элементов.");
        Server.gui.tm.fireTableDataChanged();
        return response.toString();
    }

    public String imprt() {
        String response;
        FileIO f = new FileIO();
        StringBuilder parsedArg = new StringBuilder(Commands.lastArg);
        parsedArg.delete(parsedArg.indexOf("{"), parsedArg.indexOf("{") + 1);
        parsedArg.delete(parsedArg.indexOf("}"), parsedArg.indexOf("}") + 1);
        response = "Добавление из " + parsedArg.toString().trim();
        try {
            q.addAll(f.load(parsedArg.toString().trim()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Ошибка загрузки";
        }
        Server.gui.tm.fireTableDataChanged();
        return response;
    }

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(q);
        oos.flush();
        return baos.toByteArray();
    }



}
