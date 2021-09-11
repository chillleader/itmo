package ru.ifmo.se.Karlsson;

public class LilBoy extends Human {

    LilBoy(double satisfaction) {
        super();
        this.size = satisfaction;
        name = "Малыш ";
        referName = "Малыша ";
        isMale = true;
        actions = new Action[]{new SayNothing()};
        dirActions = new DirectAction[]{new LookAt()};
    }

}
