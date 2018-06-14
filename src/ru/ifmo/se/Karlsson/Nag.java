package ru.ifmo.se.Karlsson;

public class Nag implements Action {

    @Override
    public String toString() {
        return "Nag";
    }

    @Override
    public void act(Human h) {
        System.out.print(Messages.getPhrase(h, this));
    }

}
