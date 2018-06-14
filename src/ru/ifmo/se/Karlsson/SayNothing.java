package ru.ifmo.se.Karlsson;

public class SayNothing implements Action {

    @Override
    public String toString() {
        return "SayNothing";
    }

    String malePhrase = "ещё плотнее сжал губы и не вымолвил ни слова. ";
    String femPhrase = "ещё плотнее сжала губы и не вымолвила ни слова. ";

    public void act(Human h) {
        System.out.print(Messages.getPhrase(h, this));
    }
}
