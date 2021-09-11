package ru.ifmo.se.Karlsson;

public class PhoneCallManager {

    /**
     * Обрабатывает звонок в полицию и поступающие ответы
     * @param h Звонящий
     */
    public static void callPolice(Human h) {
        System.out.print(Messages.getPhrase(h, new CallCops()));
    }

}

//todo: если много краж, то полиция не берет трубку
