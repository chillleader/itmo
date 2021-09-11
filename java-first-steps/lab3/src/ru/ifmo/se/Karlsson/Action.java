package ru.ifmo.se.Karlsson;

@FunctionalInterface
public interface Action {

    String malePhrase = "", femPhrase = "";

    void act(Human h);
}
