package ru.ifmo.se.Karlsson;

public enum Color {
    red, orange, yellow, green, blue, purple, black;

    public static String[] getStrings() {
        String[] res = new String[7];
        for (int i = 0; i < 7; i++) res[i] = (Color.values())[i].toString();
        return res;
    }
}
