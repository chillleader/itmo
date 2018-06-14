package ru.ifmo.se.Karlsson;

public class Julius extends Human {
    public Julius (double satisfaction) {
        super();
        this.size = satisfaction;
        isMale = true;
        name = "Дядя Юлиус ";
        referName = "Дядю Юлиуса ";
        actions = new Action[] { new Nag(), new CallCops() };
    }

}
