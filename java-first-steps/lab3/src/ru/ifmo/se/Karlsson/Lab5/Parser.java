package ru.ifmo.se.Karlsson.Lab5;

import com.google.gson.Gson;
import ru.ifmo.se.Karlsson.Human;
import ru.ifmo.se.Karlsson.Karlsson;


// Сериализуемые параметры: String name, referName, boolean isMale, double satisfaction, temper

public class Parser {
    private static Gson gson = new Gson();

    public static String toJson(Human h) {
        return gson.toJson(h);
    }

    public static Human fromJson(String s) {
        return gson.fromJson(s, Human.class);
    }

}
