package ru.ifmo.se.Karlsson;

public class FrekenBok extends Human {

    public FrekenBok(double satisfaction) {
        super();
        this.size = satisfaction;
        isMale = false;
        name = "Фрекен Бок ";
        referName = name;
        actions = new Action[]{new SayNothing()};
        dirActions = new DirectAction[]{new LookAt()};
    }

}
