package ru.ifmo.se.Karlsson;

public class LookAt extends DirectAction {

    @Override
    public String toString() {
        return "LookAt";
    }

    LookAt() {
        malePhrase = "тревожно посмотрел на ";
        femPhrase = "тревожно посмотрела на ";
        posMale = ", и он помахал в ответ. ";
        negMale = ", но он, казалось, ничего не слышал. ";
        posFem = ", и она помахала в ответ. ";
        negFem = ", но она, казалось, ничего на слышала. ";
    }

    @Override
    public void apply(Human actor, Human target) {
        System.out.print(Messages.getDirect(actor, target, this));
        target.act();
    }
}
