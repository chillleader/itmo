package ru.ifmo.se.Karlsson.Lab5;

import ru.ifmo.se.Karlsson.Human;
import ru.ifmo.se.Karlsson.Karlsson;
import ru.ifmo.se.Karlsson.Lab6.CollectionManager;
import ru.ifmo.se.Karlsson.Main;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collections;

/**
 * Содержит список доступных команд и их аргументы,
 * а также определяет методы, обрабатывающие команды.
 */
public enum Commands {
    /**
     * "help" выводит список доступных команд
     */
    help,

    /**
     * "old" запускает старую программу в соответствии с требованиями ЛР 4.
     */
    old,

    /**
     * "remove_lower { element }" удаляет из коллекции все элементы, меньшие, чем заданный
     */
    remove_lower,

    /**
     * "add_if_min { element }" добавляет новый элемент в коллекцию, если его значение меньше,
     * чем у наименьшего элемента данной коллекции
     */
    add_if_min,

    /**
     * "add_if_max { element }" добавляет новый элемент в коллекцию, если его значение больше,
     * чем у наибольшего элемента данной коллекции
     */
    add_if_max,

    /**
     * "import { String path }" добавляет в коллекцию все элементы из файла с указанным путем
     */
    imprt,
    exit,
    ping;

    static public Commands lastCommand;
    static public String lastArg;

    public static void setLastCommand(Commands lastCommand) {
        Commands.lastCommand = lastCommand;
    }

    public static void setLastArg(String lastArg) {
        Commands.lastArg = lastArg;
    }

    /**
     * Выполняет последнюю принятую команду
     * До вызова необходимо убедиться, что команда есть
     */
    public static String doLast() {
        Command c = () -> "Empty command";
        if (lastCommand == help) c = Commands::help;
        //if (lastCommand == old) c = Commands::old;
        if (lastCommand == add_if_min) c = Commands::add_if_min;
        if (lastCommand == add_if_max) c = Commands::add_if_max;
        if (lastCommand == remove_lower) c = Commands::remove_lower;
        if (lastCommand == imprt) c = Commands::imprt;
        if (lastCommand == ping) c = Commands::ping;
        String s = c.act();
        return s;
    }

    //далее обработчики команд

    public static String help() {
        StringBuilder sb = new StringBuilder();
        for (Commands c : Commands.values()) {
            sb.append(c.name());
            sb.append("\n");
        }
        sb.append("Образец объекта в JSON: ");
        sb.append(Parser.toJson(new Karlsson(50)));
        return sb.toString();
    }

    /*
    static String old() {
        try {
            return CollectionManager.INSTANCE.serialize();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }*/

    static String add_if_min() {
        Human h = Parser.fromJson(lastArg.trim());
        String response = CollectionManager.INSTANCE.add_if_min(h);
        return response;
    }

    static String add_if_max() {
        Human h = Parser.fromJson(lastArg.trim());
        return CollectionManager.INSTANCE.add_if_max(h);
    }


    static String remove_lower() {
        Human h = Parser.fromJson(lastArg.trim());
        return CollectionManager.INSTANCE.remove_lower(h);
    }

    static String imprt() {
        return CollectionManager.INSTANCE.imprt();
    }

    static String ping() {
        return "pong";
    }
}
